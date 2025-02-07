package com.rkecom.core.config;

import com.rkecom.crud.user.service.RoleService;
import com.rkecom.crud.user.service.UserService;
import com.rkecom.db.entity.user.Role;
import com.rkecom.db.entity.user.RoleType;
import com.rkecom.db.entity.user.User;
import com.rkecom.web.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Configuration
@RequiredArgsConstructor
public class DataInitializerConfig {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner initUserAndRoles() {
        return args -> {
            List<Role> existingRoles = roleService.findAll();
            List<RoleType> existingRoleTypes = existingRoles.stream()
                    .map(Role::getRoleType)
                    .toList ();

            List<Role> roles = Stream.of(RoleType.USER, RoleType.ADMIN, RoleType.SELLER)
                    .filter(roleType -> !existingRoleTypes.contains(roleType))
                    .map(Role::new)
                    .map(roleService::save)
                    .toList ();

            Role userRole = findRole(roles, existingRoles, RoleType.USER);
            Role adminRole = findRole(roles, existingRoles, RoleType.ADMIN);
            Role sellerRole = findRole(roles, existingRoles, RoleType.SELLER);

            // Ensure users exist before saving new ones
            createUserIfNotExists("user1", "user1pwd", "user1@email.com", userRole);
            createUserIfNotExists("admin1", "admin1pwd", "admin@email.com", adminRole);
            createUserIfNotExists("seller1", "seller1pwd", "seller1@email.com", sellerRole);
        };
    }

    private Role findRole(List<Role> newRoles, List<Role> existingRoles, RoleType roleType) {
        return existingRoles.stream()
                .filter(role -> role.getRoleType() == roleType)
                .findFirst()
                .orElseGet(() -> newRoles.stream()
                        .filter(role -> role.getRoleType() == roleType)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleType))
                );
    }

    private void createUserIfNotExists(String username, String password, String email, Role role) {
        Optional< User > existingUser = userService.findByUsername ( username );
        if (existingUser.isEmpty()) {
            UserModel user = UserModel.builder()
                    .userName(username)
                    .password(passwordEncoder.encode(password))
                    .email(email)
                    .roles(List.of(role.getRoleType().name()))
                    .build();
            userService.save(user);
        }
    }
}
