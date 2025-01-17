package com.rkecom.security.userdetails.service;

import com.rkecom.data.user.repository.UserRepository;
import com.rkecom.db.entity.user.User;
import com.rkecom.security.userdetails.UserDetailsImpl;
import com.rkecom.ui.model.user.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
        User user=userRepository.findUserByUserName ( username )
                .orElseThrow ( () -> new UsernameNotFoundException ( "User Not Found" ) );

        return UserDetailsImpl.builder ()
                .user ( UserModel.builder ()
                        .userId ( user.getUserId () )
                        .userName ( user.getUserName () )
                        .password ( user.getPassword () )
                        .roles ( user.getRoles ().stream ()
                                .map ( role -> role.getRoleType ().name () )
                                .toList ())
                        .email ( user.getEmail () )
                        .build ())
                .authorities ( user.getRoles ()
                        .stream ()
                        .map ( role->new SimpleGrantedAuthority ( role.getRoleType ().name () ) )
                        .toList () )
                .build();
    }
}
