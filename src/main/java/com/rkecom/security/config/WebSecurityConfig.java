package com.rkecom.security.config;

import com.rkecom.security.jwt.filter.JwtAuthenticationFilter;
import com.rkecom.security.jwt.util.JwtAuthenticationEntryPoint;
import com.rkecom.security.userdetails.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WebSecurityConfig {
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    public SecurityFilterChain configure( HttpSecurity http) throws Exception {
        return http.csrf (csrfConfigurer -> csrfConfigurer.disable())
                .exceptionHandling (exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint ( jwtAuthenticationEntryPoint ))
                .sessionManagement (sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy ( SessionCreationPolicy.STATELESS ))
                .authorizeHttpRequests ( authorizeHttpRequestsConfigurer ->
                        authorizeHttpRequestsConfigurer.requestMatchers ( "/api/auth/**" ).permitAll ()
                                .requestMatchers ( "/api/public/**" ).permitAll ()
                                .requestMatchers ( "/swagger-ui/**" ).permitAll ()
                                .anyRequest ().authenticated ()
                ).authenticationProvider ( authenticationProvider () )
                .addFilterBefore ( jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class )
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
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (
                web -> web.ignoring().requestMatchers("/static/**", "/api-docs/**")
        );
    }
    
}
