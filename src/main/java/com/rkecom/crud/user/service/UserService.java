package com.rkecom.crud.user.service;

import com.rkecom.core.response.ApiResponse;
import com.rkecom.crud.core.service.BaseService;
import com.rkecom.web.user.model.UserModel;

public interface UserService extends BaseService {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    UserModel save(UserModel user);
    ApiResponse <UserModel> getAllUsers(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
    ApiResponse <UserModel> getAllNonAdminUsers(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);

}
