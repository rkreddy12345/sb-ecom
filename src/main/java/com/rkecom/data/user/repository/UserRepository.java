package com.rkecom.data.user.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.user.RoleType;
import com.rkecom.db.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository < User, Long> {
    Optional<User> findUserByUserName(String username);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
    @Query ("SELECT u FROM User u WHERE NOT EXISTS (SELECT 1 FROM u.roles r WHERE r.roleType = :roleType)")
    Page <User> findAllExcludingRole( @Param ( "roleType" ) RoleType roleType, Pageable pageable );
}
