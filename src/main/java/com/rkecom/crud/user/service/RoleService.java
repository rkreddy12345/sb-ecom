package com.rkecom.crud.user.service;

import com.rkecom.crud.core.service.BaseService;
import com.rkecom.db.entity.user.Role;
import com.rkecom.db.entity.user.RoleType;

import java.util.List;

public interface RoleService extends BaseService {
    Role findByRoleType( RoleType roleType);
    boolean existsByRoleType( RoleType roleType);
    Role save(Role role);
    List <Role> findAll();
}
