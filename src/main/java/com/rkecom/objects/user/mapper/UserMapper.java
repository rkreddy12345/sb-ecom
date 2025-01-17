package com.rkecom.objects.user.mapper;

import com.rkecom.crud.user.service.RoleService;
import com.rkecom.db.entity.user.Role;
import com.rkecom.db.entity.user.RoleType;
import com.rkecom.db.entity.user.User;
import com.rkecom.ui.model.user.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserMapper {
    private final RoleService roleService;
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
}
