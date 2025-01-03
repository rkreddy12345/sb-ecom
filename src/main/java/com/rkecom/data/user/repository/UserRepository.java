package com.rkecom.data.user.repository;

import com.rkecom.db.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository< User, Long> {
    Optional<User> findUserByUserName(String username);
}
