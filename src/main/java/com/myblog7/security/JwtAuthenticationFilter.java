package com.myblog7.security;


import com.myblog7.exception.BlogAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private  JwtTokenProvider jwtTokenProvider;

    @Autowired
    private  CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        get jwt token from http request
        String token = getJWTFromRequest(request);
//        validate token
        try {
            if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
    //            get username from token
                String username= jwtTokenProvider.getUsenameFromJWT(token);
    //            load user associated with token
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

//                compare username and password
                UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(
                        userDetails,null, userDetails.getAuthorities()
                );

//                set spring security
                SecurityContextHolder.getContext().setAuthentication(authenticationToken
                );
            }
        } catch (BlogAPIException e) {
            throw new RuntimeException(e);
        }
    }

    private String getJWTFromRequest(HttpServletRequest request) {

        String bearerToken= request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
