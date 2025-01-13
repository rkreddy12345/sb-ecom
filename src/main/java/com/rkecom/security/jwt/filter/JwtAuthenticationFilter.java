package com.rkecom.security.jwt.filter;

import com.rkecom.security.jwt.util.JwtUtil;
import com.rkecom.security.userdetails.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal ( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {
        try{
            String jwtToken= Objects.requireNonNull ( jwtUtil ).getJwtFromHeader ( request );
            if(Objects.nonNull (jwtToken) && jwtUtil.validateJwtToken (jwtToken)){
                String username = jwtUtil.getUsernameFromJwtToken(jwtToken);
                UserDetails userDetails =  userDetailsService .loadUserByUsername (username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails ( new WebAuthenticationDetailsSource ().buildDetails ( request ) );
                SecurityContextHolder.getContext().setAuthentication (authentication);
            }
        }catch (Exception e){
            logger.error (e.getMessage(),e);
        }
        filterChain.doFilter (request, response);
    }
}
