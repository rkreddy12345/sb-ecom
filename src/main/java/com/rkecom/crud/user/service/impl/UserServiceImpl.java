package com.rkecom.crud.user.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.crud.user.service.UserService;
import com.rkecom.data.user.repository.UserRepository;
import com.rkecom.objects.user.mapper.UserMapper;
import com.rkecom.ui.model.user.UserModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername ( String username ) {
       return userRepository.existsByUserName ( username );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail ( String email ) {
        return userRepository.existsByEmail ( email );
    }

    @Override
    public UserModel save ( UserModel userModel ) {
        if(userRepository.existsByUserName ( userModel.getUserName ())) {
            throw new ApiException ( "username already exists" );
        }
        if (userRepository.existsByEmail ( userModel.getEmail ())) {
            throw new ApiException ( "email already exists" );
        }
        return userMapper.toModel ().apply ( userRepository.save (userMapper.toEntity ().apply ( userModel )) );
    }
}
