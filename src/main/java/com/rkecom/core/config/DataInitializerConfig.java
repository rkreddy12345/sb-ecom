package com.rkecom.core.config;

import com.rkecom.crud.user.service.RoleService;
import com.rkecom.crud.user.service.UserService;
import com.rkecom.db.entity.user.Role;
import com.rkecom.db.entity.user.RoleType;
import com.rkecom.ui.model.user.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DataInitializerConfig {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner innitUserAndRoles() {
        return args -> {
            Role userRole = roleService.save(new Role(RoleType.USER));
            Role adminRole = roleService.save(new Role(RoleType.ADMIN));
            Role sellerRole = roleService.save(new Role ( RoleType.SELLER));

            UserModel user1 = UserModel.builder()
                    .userName("user1")
                    .password(passwordEncoder.encode("user1pwd"))
                    .email("user1@email.com")
                    .roles(new ArrayList<>(List.of(userRole))) // Initialize roles
                    .build();
            userService.save(user1);

            UserModel admin1 = UserModel.builder()
                    .userName("admin1")
                    .password(passwordEncoder.encode("admin1pwd"))
                    .email("admin@email.com")
                    .roles(new ArrayList <> ( List.of(adminRole))) // Initialize roles
                    .build();
            userService.save(admin1);

            UserModel seller1 = UserModel.builder()
                    .userName("seller1")
                    .password(passwordEncoder.encode("seller1pwd"))
                    .email("seller1@email.com")
                    .roles(new ArrayList<>(List.of(sellerRole))) // Initialize roles
                    .build();
            userService.save(seller1);
        };
    }

}
