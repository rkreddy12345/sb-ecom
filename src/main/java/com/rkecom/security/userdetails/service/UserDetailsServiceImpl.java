package com.rkecom.security.userdetails.service;

import com.rkecom.data.user.repository.UserRepository;
import com.rkecom.db.entity.user.User;
import com.rkecom.objects.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername ( String username ) throws UsernameNotFoundException {
        User user=userRepository.findUserByUserName ( username )
                .orElseThrow ( () -> new UsernameNotFoundException ( "User Not Found" ) );

        return userMapper.toEcomUserDetailsFromUser ().apply ( user );
    }
}
