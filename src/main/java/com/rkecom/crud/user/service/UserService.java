package com.rkecom.crud.user.service;

import com.rkecom.core.response.ApiResponse;
import com.rkecom.crud.core.service.BaseService;
import com.rkecom.db.entity.user.User;
import com.rkecom.web.user.model.UserModel;
import com.rkecom.web.user.model.UserModelWithoutRoles;

import java.util.Optional;

public interface UserService extends BaseService {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    UserModel save(UserModel user);
    ApiResponse <UserModel> getAllUsers(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
    ApiResponse <UserModel> getAllNonAdminUsers(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
    Optional < User > findByUsername( String username);
    UserModelWithoutRoles getCurrentUserAddresses ( String username );
}
