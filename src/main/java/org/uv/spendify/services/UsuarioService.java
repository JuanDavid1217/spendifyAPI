/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.uv.spendify.dtos.usuarios.UsuarioAcces;
import org.uv.spendify.dtos.usuarios.UsuarioLogin;
import org.uv.spendify.dtos.usuarios.UsuarioNewAccount;
import org.uv.spendify.dtos.usuarios.UsuarioPassword;
import org.uv.spendify.dtos.usuarios.UsuarioPasswordBase;
import org.uv.spendify.converters.usuarios.UsuarioAccessConverter;
import org.uv.spendify.converters.usuarios.UsuarioNewAccountConverter;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.models.Usuario;
import org.uv.spendify.repository.UsuarioRepository;
import org.uv.spendify.security.JWTUtils;
import static org.uv.spendify.validations.Validation.*;

/**
 *
 * @author juan
 */
@Service
public class UsuarioService {
    private final UsuarioRepository repositorio;
    private final UsuarioAccessConverter usuarioAccessConverter;
    private final UsuarioNewAccountConverter usuarioNewAccountConverter;
    private final PasswordEncoder pe;
    private final JWTUtils jwtUtils;
    private String message="Invalid Password.";
    
    public UsuarioService(UsuarioRepository repositorio, UsuarioAccessConverter converter1,
            UsuarioNewAccountConverter converter2,
            PasswordEncoder pe, JWTUtils jwtUtils){
        this.repositorio=repositorio;
        this.usuarioAccessConverter=converter1;
        this.usuarioNewAccountConverter=converter2;
        this.pe=pe;
        this.jwtUtils=jwtUtils;
    }
    
    public UsuarioAcces createAccount(UsuarioNewAccount newAccount){
        Usuario usuario=repositorio.findByEmail(newAccount.getEmail());
        if(usuario==null){
            usuario=usuarioNewAccountConverter.dtotoEntity(newAccount);
            if(passwordValidation(usuario.getPassword())){
                if(edadValidation(usuario.getEdad())){
                    if(telefonoValidation(usuario.getTelefono())){
                        try{
                            String passwordEncriptada=pe.encode(usuario.getPassword());
                            usuario.setPassword(passwordEncriptada);
                            usuario=repositorio.save(usuario);
                            return usuarioAccessConverter.entitytoDTO(usuario);
                        }catch(Exception ex){
                            throw new Exceptions(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    }else{
                        throw new Exceptions("Invalid phone number.", HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions("conflict with your age.", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(message, HttpStatus.CONFLICT);
            }
        }else{
            return null;     
        }
    }
    
    public boolean deleteAccount(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u=repositorio.findByEmail(email);
        if(u!=null){
            repositorio.delete(u);
            return true;
        }else{
            return false;
        }
    }
    
    public UsuarioAcces updateAccount(UsuarioAcces usuario){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u=repositorio.findByEmail(email);
        Usuario validated = repositorio.findByEmail(usuario.getEmail());
        if(u!=null){
            if(validated==null){
                if(edadValidation(usuario.getEdad())){
                        if(telefonoValidation(usuario.getTelefono())){
                            u.setEdad(usuario.getEdad());
                            u.setEmail(usuario.getEmail());
                            u.setTelefono(usuario.getTelefono());
                            u.setNombre(usuario.getNombre());
                            u.setApellidoPaterno(usuario.getApellidoPaterno());
                            u.setApellidoMaterno(usuario.getApellidoMaterno());
                            u=repositorio.save(u);
                            usuario=usuarioAccessConverter.entitytoDTO(u);
                            return usuario;
                        }else{
                            throw new Exceptions("Invalid phone number.", HttpStatus.CONFLICT);
                        }
                    }else{
                        throw new Exceptions("conflict with your age.", HttpStatus.CONFLICT);
                    }
            }else{
                throw new Exceptions("Email already registered.", HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean changePassword(UsuarioPassword changePassword){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u=repositorio.findByEmail(email);
        if(u!=null){
            if(passwordValidation(changePassword.getNewPassword())){
                try{
                    if(pe.matches(changePassword.getOldPasswaord(), u.getPassword())){
                        String password=pe.encode(changePassword.getNewPassword());
                        u.setPassword(password);
                        repositorio.save(u);
                        return true;
                    }else{
                        throw new Exceptions("Incorrect actual password.", HttpStatus.CONFLICT);
                    }
                }catch(Exception ex){
                    throw new Exceptions(ex.getMessage(), HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(message, HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public boolean changePasswordWithKey(UsuarioPasswordBase changePassword, long id){
        Optional<Usuario> u=repositorio.findById(id);
        if(!u.isEmpty()){
            Usuario us=u.get();
            if(passwordValidation(changePassword.getNewPassword())){
                try{
                    String password=pe.encode(changePassword.getNewPassword());
                    us.setPassword(password);
                    repositorio.save(us);
                    return true;
                }catch(Exception ex){
                    throw new Exceptions(ex.getMessage(), HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(message, HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public UsuarioAcces myInfo(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u=repositorio.findByEmail(email);
        if(u!=null){
            return usuarioAccessConverter.entitytoDTO(u);
        }else{
            return null;
        }
    }
    
    public Usuario userbyId(long id){
        Optional<Usuario> u=repositorio.findById(id);
        if(!u.isEmpty()){
            return u.get();
        }else{
            return null;
        }
    }
    
    public Usuario userbyEmail(String email){
        Usuario u=repositorio.findByEmail(email);
        if(u!=null){
            return u;
        }else{
            return null;
        }
    }
}
