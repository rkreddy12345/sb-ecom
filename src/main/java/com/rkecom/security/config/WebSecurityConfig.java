package com.rkecom.security.config;

import com.rkecom.db.entity.user.RoleType;
import com.rkecom.security.jwt.filter.JwtAuthenticationFilter;
import com.rkecom.security.jwt.util.JwtAuthenticationEntryPoint;
import com.rkecom.security.userdetails.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebSecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain configure( HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests ( authorizeHttpRequestsConfigurer ->
                        authorizeHttpRequestsConfigurer.requestMatchers ( "/api/v1/auth/**" ).permitAll ()
                                .requestMatchers ( "/api/v1/public/**" ).permitAll ()
                                .requestMatchers ( "/h2-console/**" ).permitAll ()
                                .anyRequest ().authenticated ())
                .csrf (csrf->{
                    csrf.ignoringRequestMatchers ( "/h2-console/**" );
                    csrf.disable ();
                })
                .headers (headersConfigurer->headersConfigurer.frameOptions (
                        frameOptionsConfig -> frameOptionsConfig.sameOrigin ()
                ))
                .sessionManagement (sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy ( SessionCreationPolicy.STATELESS ))
                .authenticationProvider ( authenticationProvider () )
                .addFilterBefore ( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class )
                .exceptionHandling (exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint ( jwtAuthenticationEntryPoint ))
                .build ();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder ( passwordEncoder() );
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder ( ) {
        return new BCryptPasswordEncoder ();
    }

    @Bean
    public AuthenticationManager authenticationManager ( AuthenticationConfiguration  authConfig) throws Exception {
        return authConfig.getAuthenticationManager ();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix ()
                .role ( RoleType.ADMIN.name () )
                .implies ( RoleType.USER.name (), RoleType.SELLER.name () )
                .build ();

    }

}
