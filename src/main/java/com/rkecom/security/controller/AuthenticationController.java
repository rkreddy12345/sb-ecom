package com.rkecom.security.controller;

import com.rkecom.core.controller.BaseController;
import com.rkecom.core.response.util.ErrorResponseUtil;
import com.rkecom.crud.user.service.RoleService;
import com.rkecom.crud.user.service.UserService;
import com.rkecom.data.user.repository.UserRepository;
import com.rkecom.db.entity.user.Role;
import com.rkecom.db.entity.user.RoleType;
import com.rkecom.security.jwt.util.JwtUtil;
import com.rkecom.security.ui.model.LoginRequest;
import com.rkecom.security.ui.model.SignupRequest;
import com.rkecom.security.ui.model.UserInfoResponse;
import com.rkecom.security.userdetails.UserDetailsImpl;
import com.rkecom.ui.model.user.UserModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController("/api/v1/auth")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthenticationController extends BaseController {

    private static final Logger logger= LoggerFactory.getLogger ( AuthenticationController.class );

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if(userService.existsByUsername ( signupRequest.getUsername() )){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorResponseUtil.buildErrorResponse ( "ERROR", "user name is already taken", HttpStatus.CONFLICT )
            );
        }
        if(userService.existsByEmail ( signupRequest.getEmail() )){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorResponseUtil.buildErrorResponse ( "ERROR", "email is already taken", HttpStatus.CONFLICT )
            );
        }
        UserModel userModel=UserModel.builder ().userName ( signupRequest.getUsername())
                .email ( signupRequest.getEmail() ).password ( passwordEncoder.encode ( signupRequest.getPassword () ) ).build();

        List<String> roleNames=signupRequest.getRoles();
        List<Role> roles=new ArrayList <> ();
        if(Objects.isNull (roleNames)){
            Role userRole=roleService.findByRoleType ( RoleType.USER );
            roles.add ( userRole );
        }else {
            roleNames.forEach(roleName -> {
                switch (roleName) {
                    case "admin" -> {
                        Role adminRole = roleService.findByRoleType(RoleType.ADMIN);
                        roles.add(adminRole);
                    }
                    case "seller" -> {
                        Role sellerRole = roleService.findByRoleType(RoleType.SELLER);
                        roles.add(sellerRole);
                    }
                    default -> {
                        Role userRole = roleService.findByRoleType(RoleType.USER);
                        roles.add(userRole);
                    }
                }
            });
        }
        userModel.setRoles ( roles );
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save ( userModel ));
    }

    @PostMapping ("/login")
    public ResponseEntity <Object> authenticateUser( @RequestBody LoginRequest loginRequest ) {
        Authentication authentication;
        try{
            authentication=authenticationManager.authenticate ( new UsernamePasswordAuthenticationToken (loginRequest.getUsername(), loginRequest.getPassword()) );
        } catch (AuthenticationException e) {
            final Map <String, Object> errorDetails = new HashMap <> ();
            errorDetails.put ( "status", HttpStatus.NOT_FOUND.value() );
            errorDetails.put ( "message", e.getMessage() );
            errorDetails.put("timestamp", System.currentTimeMillis());
            return ResponseEntity.status ( HttpStatus.NOT_FOUND ).body ( errorDetails );
        }
        SecurityContextHolder.getContext ().setAuthentication ( authentication );
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal ();
        String jwtToken= jwtUtil.generateJwtToken ( userDetails );
        List <String> roles=userDetails.getAuthorities ()
                .stream ().map ( GrantedAuthority::getAuthority )
                .toList ();

        UserInfoResponse response=UserInfoResponse.builder ()
                .id ( userDetails.getUserId () )
                .token ( jwtToken )
                .username ( userDetails.getUsername () )
                .roles ( roles )
                .build ();
        return ResponseEntity.ok (response);
    }

}
