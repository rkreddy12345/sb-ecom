package com.rkecom.crud.user.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.core.response.ApiResponse;
import com.rkecom.core.response.util.ApiResponseUtil;
import com.rkecom.core.util.PaginationUtil;
import com.rkecom.crud.user.service.UserService;
import com.rkecom.data.user.repository.UserRepository;
import com.rkecom.db.entity.user.RoleType;
import com.rkecom.db.entity.user.User;
import com.rkecom.objects.user.mapper.UserMapper;
import com.rkecom.web.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private static final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public boolean existsByUsername ( String username ) {
       return userRepository.existsByUserName ( username );
    }

    @Override
    public boolean existsByEmail ( String email ) {
        return userRepository.existsByEmail ( email );
    }

    @Transactional
    @Override
    public UserModel save ( UserModel userModel ) {
        if(userRepository.existsByUserName ( userModel.getUserName ())) {
            throw new ApiException ( "username already exists" );
        }
        if (userRepository.existsByEmail ( userModel.getEmail ())) {
            throw new ApiException ( "email already exists" );
        }
        User user=userRepository.save (userMapper.toEntity ().apply ( userModel ));
        return userMapper.toModel ().apply (user ) ;
    }

    @Override
    public ApiResponse < UserModel > getAllUsers (Integer pageNo, Integer pageSize, String sortBy, String sortOrder ) {
        Sort sort=sortOrder.equalsIgnoreCase ( PaginationUtil.SORT_IN_ASC )
                ?Sort.by ( Sort.Direction.ASC, sortBy )
                : Sort.by ( Sort.Direction.DESC, sortBy );
        Page <User> userPage=userRepository.findAll (PageRequest.of (pageNo, pageSize, sort));
        return buildUserApiResponse(userPage);
    }

    @Override
    public ApiResponse < UserModel > getAllNonAdminUsers (Integer pageNo, Integer pageSize, String sortBy, String sortOrder ) {
        Sort sort=sortOrder.equalsIgnoreCase ( PaginationUtil.SORT_IN_ASC )
                ?Sort.by ( Sort.Direction.ASC, sortBy )
                : Sort.by ( Sort.Direction.DESC, sortBy );
        Page <User> userPage=userRepository.findAllExcludingRole ( RoleType.ADMIN , PageRequest.of ( pageNo, pageSize, sort ));
        return buildUserApiResponse(userPage);
    }


    private ApiResponse < UserModel > buildUserApiResponse ( Page < User > userPage ) {
        List <User> users = userPage.getContent ();
        if(users.isEmpty()) {
            throw new ApiException ( "No users found" );
        }else{
            List<UserModel> userModels = users.stream()
                    .map ( userMapper.toModel() )
                    .toList ();

            return ApiResponseUtil.buildApiResponse ( userModels, userPage );
        }
    }
}
