package com.rkecom.security.controller;

import com.rkecom.core.controller.BaseController;
import com.rkecom.core.response.util.ErrorConstants;
import com.rkecom.core.response.util.ErrorResponseUtil;
import com.rkecom.crud.user.service.RoleService;
import com.rkecom.crud.user.service.UserService;
import com.rkecom.db.entity.user.Role;
import com.rkecom.db.entity.user.RoleType;
import com.rkecom.security.auth.util.JwtUtil;
import com.rkecom.web.security.model.LoginRequest;
import com.rkecom.web.security.model.SignupRequest;
import com.rkecom.web.security.model.UserInfoResponse;
import com.rkecom.security.userdetails.EcomUserDetails;
import com.rkecom.web.user.model.UserModel;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthenticationController extends BaseController {

    private static final Logger logger= LoggerFactory.getLogger ( AuthenticationController.class );

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if(userService.existsByUsername ( signupRequest.getUsername() )){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorResponseUtil.buildErrorResponse ( ErrorConstants.ERROR_STATUS, "user name is already taken", HttpStatus.CONFLICT )
            );
        }
        if(userService.existsByEmail ( signupRequest.getEmail() )){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    ErrorResponseUtil.buildErrorResponse ( ErrorConstants.ERROR_STATUS, "email is already taken", HttpStatus.CONFLICT )
            );
        }
        UserModel userModel=UserModel.builder ()
                .userName ( signupRequest.getUsername())
                .email ( signupRequest.getEmail() )
                .password ( passwordEncoder.encode ( signupRequest.getPassword () ) )
                .build();

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
        userModel.setRoles ( roles.stream ()
                .map ( role -> role.getRoleType ().name () )
                .toList ());
        UserModel registeredUser = userService.save ( userModel );
        Map<String, Object> signUpResponse= new LinkedHashMap <>();
        signUpResponse.put ( "message", "Registration Successful." );
        signUpResponse.put("details", registeredUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(signUpResponse);
    }

    @PostMapping ("/login")
    public ResponseEntity <Object> authenticateUser( @Valid @RequestBody LoginRequest loginRequest ) {
        Authentication authentication;
        try{
            authentication=authenticationManager.authenticate ( new UsernamePasswordAuthenticationToken (loginRequest.getUsername(), loginRequest.getPassword()) );
        } catch (AuthenticationException e) {
            return ResponseEntity.status ( HttpStatus.UNAUTHORIZED )
                    .body ( ErrorResponseUtil.buildErrorResponse (
                            ErrorConstants.ERROR_STATUS,
                            "Login failed.",
                            HttpStatus.UNAUTHORIZED
                    ) );
        }
        SecurityContextHolder.getContext ().setAuthentication ( authentication );
        EcomUserDetails userDetails= (EcomUserDetails) authentication.getPrincipal ();
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
        Map<String, Object> loginResponse= new LinkedHashMap <>();
        loginResponse.put ( "message", "Login Successful." );
        loginResponse.put("details", response);
        return ResponseEntity.status ( HttpStatus.ACCEPTED ).body ( loginResponse );
    }

}
