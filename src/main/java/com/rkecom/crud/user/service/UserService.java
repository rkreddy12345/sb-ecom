package com.rkecom.crud.user.service;

import com.rkecom.crud.core.service.BaseService;
import com.rkecom.ui.model.user.UserModel;

public interface UserService extends BaseService {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    UserModel save(UserModel user);
}
