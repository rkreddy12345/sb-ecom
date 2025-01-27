package com.rkecom.security.auth.util;

import com.rkecom.objects.user.mapper.UserMapper;
import com.rkecom.security.userdetails.EcomUserDetails;
import com.rkecom.web.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserDetailsUtil {

    private final UserMapper userMapper;

    public Optional<UserModel> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable ( authentication )
                .filter ( authToken-> authToken.isAuthenticated () && authToken.getPrincipal () instanceof EcomUserDetails )
                .map ( principal->(EcomUserDetails)principal.getPrincipal () )
                .map ( userMapper.toModelFromEcomUserDetails () );
    }
}
