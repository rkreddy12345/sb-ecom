package com.rkecom.objects.user.mapper;

import com.rkecom.db.entity.user.User;
import com.rkecom.ui.model.user.UserModel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapper {
    public Function< UserModel, User > toEntity(){
        return userModel->User.builder ()
                .userName ( userModel.getUserName () )
                .email ( userModel.getEmail () )
                .password ( userModel.getPassword () )
                .roles ( userModel.getRoles () )
                .build ();
    }

    public Function<User, UserModel> toModel(){
        return user -> UserModel.builder ()
                .userId ( user.getUserId () )
                .userName ( user.getUserName () )
                .email ( user.getEmail () )
                .roles ( user.getRoles () )
                .build ();
    }
}
