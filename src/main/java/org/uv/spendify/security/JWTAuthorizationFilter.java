/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author juan
 */
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter{
    
    private final JWTUtils jwtUtils;
    private final UserDetailsServiceImp userDetailsServiceImp;
    
    public JWTAuthorizationFilter(JWTUtils jwtUtils,
                                  UserDetailsServiceImp userDetailsServiceImp){
        this.jwtUtils=jwtUtils;
        this.userDetailsServiceImp=userDetailsServiceImp;
    }
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {
        String tokenHeader= request.getHeader("Authorization");
        
        if(tokenHeader!=null && tokenHeader.startsWith("Bearer")){
            String token=tokenHeader.substring(7);
            if(jwtUtils.isTokenValue(token)){
                String email=jwtUtils.getUserNameFromToken(token);
                UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(email, null, userDetails.getAuthorities());
                
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
    
}
