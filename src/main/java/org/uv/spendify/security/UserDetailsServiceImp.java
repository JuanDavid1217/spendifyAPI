/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.uv.spendify.models.Usuario;
import org.uv.spendify.repository.UsuarioRepository;

/**
 *
 * @author juan
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService{
    private final UsuarioRepository repository;
    
    public UserDetailsServiceImp(UsuarioRepository repository){
        this.repository=repository;
    }
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario userDetails= repository.findByEmail(userName);
        
        if(userDetails==null){
            throw new UsernameNotFoundException("El usuario "+userName+" no exite");
        }else{
            GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_USER");
            List<GrantedAuthority> list=new ArrayList<>();
            list.add(authority);

            Collection<? extends GrantedAuthority> authorities=list;

            return new User(userName, userDetails.getPassword(), true,
            true, true, true, authorities);
            
        }
        
        
        
    }
    
}
