package com.rkecom.objects.user.mapper;

import com.rkecom.crud.user.service.RoleService;
import com.rkecom.db.entity.user.Role;
import com.rkecom.db.entity.user.RoleType;
import com.rkecom.db.entity.user.User;
import com.rkecom.security.userdetails.EcomUserDetails;
import com.rkecom.web.user.model.AddressModel;
import com.rkecom.web.user.model.UserModel;
import com.rkecom.web.user.model.UserModelWithoutRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserMapper {
    private final RoleService roleService;
    private final AddressMapper addressMapper;
    public Function< UserModel, User > toEntity(){
       return userModel -> {
           List < Role > roles = userModel.getRoles ().stream ()
                   .map ( roleName-> roleService.findByRoleType ( RoleType.valueOf ( roleName ) ) )
                   .toList ();

           return User.builder()
                   .userId ( userModel.getUserId () )
                   .userName ( userModel.getUserName () )
                   .password ( userModel.getPassword () )
                   .email ( userModel.getEmail () )
                   .roles ( roles )
                   .build();
       };
    }

    public Function<User, UserModel> toModel(){
        return user -> UserModel.builder ()
                .userId ( user.getUserId () )
                .userName ( user.getUserName () )
                .email ( user.getEmail () )
                .roles ( user.getRoles ().stream ()
                        .map ( role -> role.getRoleType ().name () )
                        .toList ())
                .build ();
    }

    public Function<User, EcomUserDetails > toEcomUserDetailsFromEntity() {
        return user -> {
            UserModel userModel = UserModel.builder()
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().stream()
                            .map(role -> role.getRoleType().name())
                            .toList())
                    .email(user.getEmail())
                    .build();

            List< SimpleGrantedAuthority > authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRoleType().name()))
                    .toList();

            return EcomUserDetails.builder()
                    .user(userModel)
                    .authorities(authorities)
                    .build();
        };
    }

    public Function< EcomUserDetails, UserModel> toModelFromEcomUserDetails() {
        return userDetails -> UserModel.builder()
                .userId(userDetails.getUserId())
                .userName(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(userDetails.getAuthorities().stream()
                        .map(authority -> authority.getAuthority())
                        .toList())
                .build();
    }

    // Map User to UserModelWithoutRoles (excluding roles)
    public Function<User, UserModelWithoutRoles> toUserModelWithoutRoles() {
        return user -> {
            List<AddressModel> addressModels = user.getAddresses().stream()
                    .map(address -> addressMapper.toModel ().apply ( address ))
                    .toList ();

            return UserModelWithoutRoles.builder()
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .email(user.getEmail())
                    .addresses(addressModels)
                    .build();
        };
    }
}
