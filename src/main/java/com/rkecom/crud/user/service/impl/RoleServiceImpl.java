package com.rkecom.crud.user.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.core.exception.ResourceNotFoundException;
import com.rkecom.core.util.ResourceConstants;
import com.rkecom.crud.user.service.RoleService;
import com.rkecom.data.user.repository.RoleRepository;
import com.rkecom.db.entity.user.Role;
import com.rkecom.db.entity.user.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    @Transactional(readOnly = true)
    public Role findByRoleType ( RoleType roleType ) {
        return roleRepository.findByRoleType ( roleType )
                .orElseThrow (()->new ResourceNotFoundException ( ResourceConstants.ROLE, "roleName", roleType ) );
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRoleType ( RoleType roleType ) {
        return roleRepository.existsByRoleType ( roleType );
    }

    @Override
    public Role save ( Role role ) {
        if(existsByRoleType ( role.getRoleType () )) {
            throw new ApiException ( "role already exists with "+role.getRoleType ().name () );
        }
        return roleRepository.save(role);
    }
}
