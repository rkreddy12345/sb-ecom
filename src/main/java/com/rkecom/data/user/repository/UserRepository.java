package com.rkecom.data.user.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.user.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository < User, Long> {
    Optional<User> findUserByUserName(String username);
    boolean existsByUserName(String username);
    boolean existsByEmail(String email);
}
