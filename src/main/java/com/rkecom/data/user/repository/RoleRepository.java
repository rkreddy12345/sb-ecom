package com.rkecom.data.user.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.user.Role;
import com.rkecom.db.entity.user.RoleType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository< Role, Integer> {
    Optional<Role> findByRoleType( RoleType roleType);
    boolean existsByRoleType(RoleType roleType);
}
