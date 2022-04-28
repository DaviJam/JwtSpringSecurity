package com.ensup.jwtsecuritytest.filter;

import com.ensup.jwtsecuritytest.service.CustomUserDetailsService;
import com.ensup.jwtsecuritytest.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, RuntimeException {
        String authorizationHeader = request.getHeader("Authorization");
        String token=null;
        String namespace = "Bearer ";
        String userName = null;

        if(authorizationHeader != null && authorizationHeader.startsWith(namespace)){
            token = authorizationHeader.substring(namespace.length());
            try {
                userName = jwtUtil.extractUsername(token);
            } catch (Exception e) {
               throw new RuntimeException(e.getMessage());
            }
        }
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
            try {
                if (jwtUtil.validateToken(token, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
