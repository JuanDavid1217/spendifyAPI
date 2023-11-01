/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.spendify.DTOs.usuarios.UsuarioAcces;
import org.uv.spendify.DTOs.usuarios.UsuarioLogin;
import org.uv.spendify.DTOs.usuarios.UsuarioNewAccount;
import org.uv.spendify.DTOs.usuarios.UsuarioPassword;
import org.uv.spendify.converters.usuarios.UsuarioAccessConverter;
import org.uv.spendify.converters.usuarios.UsuarioLoginConverter;
import org.uv.spendify.converters.usuarios.UsuarioNewAccountConverter;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.models.Usuario;
import org.uv.spendify.repository.UsuarioRepository;
import static org.uv.spendify.validaciones.Validacion.*;

/**
 *
 * @author juan
 */
@Service
public class UsuarioService {
    private final UsuarioRepository repositorio;
    private final UsuarioAccessConverter usuarioAccessConverter;
    private final UsuarioNewAccountConverter usuarioNewAccountConverter;
    private final UsuarioLoginConverter usuarioLoginConverter;
    
    public UsuarioService(UsuarioRepository repositorio, UsuarioAccessConverter converter1,
            UsuarioNewAccountConverter converter2, UsuarioLoginConverter converter3){
        this.repositorio=repositorio;
        this.usuarioAccessConverter=converter1;
        this.usuarioNewAccountConverter=converter2;
        this.usuarioLoginConverter=converter3;
    }
    
    public UsuarioAcces createAccount(UsuarioNewAccount newAccount){
        Usuario usuario=repositorio.findByEmail(newAccount.getEmail());
        if(usuario==null){
            usuario=usuarioNewAccountConverter.DTOtoEntity(newAccount);
            if(passwordValidation(usuario.getPassword())){
                if(edadValidation(usuario.getEdad())){
                    if(telefonoValidation(usuario.getTelefono())){
                        try{
                            String passwordEncriptada=encriptar(usuario.getPassword());
                            usuario.setPassword(passwordEncriptada);
                            usuario=repositorio.save(usuario);
                            return usuarioAccessConverter.EntitytoDTO(usuario);
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
                throw new Exceptions("Invalid password", HttpStatus.CONFLICT);
            }
        }else{
            return null;     
        }
    }
    
    public boolean deleteAccount(long id){
        Optional<Usuario> usuario=repositorio.findById(id);
        if(!usuario.isEmpty()){
            Usuario u=usuario.get();
            repositorio.delete(u);
            return true;
        }else{
            return false;
        }
    }
    
    public UsuarioAcces updateAccount(UsuarioAcces usuario, long id){
        Optional<Usuario> u=repositorio.findById(id);
        if(!u.isEmpty()){
            Usuario us=u.get();
            us.setEdad(usuario.getEdad());
            us.setEmail(usuario.getEmail());
            us.setTelefono(usuario.getTelefono());
            us.setNombre(usuario.getNombre());
            us.setApellidoPaterno(usuario.getNombre());
            us.setApellidoMaterno(usuario.getApellidoMaterno());
            us=repositorio.save(us);
            usuario=usuarioAccessConverter.EntitytoDTO(us);
            return usuario;
        }else{
            return null;
        }
    }
    
    public boolean changePassword(UsuarioPassword changePassword, long id){
        Optional<Usuario> u=repositorio.findById(id);
        if(!u.isEmpty()){
            Usuario us=u.get();
            if(passwordValidation(changePassword.getNewPassword())){
                try{
                    String password=desencriptar(us.getPassword());
                    if(changePassword.getOldPasswaord().equals(password)){
                        password=encriptar(changePassword.getNewPassword());
                        us.setPassword(password);
                        repositorio.save(us);
                        return true;
                    }else{
                        throw new Exceptions("Incorrect actual password.", HttpStatus.CONFLICT);
                    }
                }catch(Exception ex){
                    throw new Exceptions(ex.getMessage(), HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("Invalid password", HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public UsuarioAcces login(UsuarioLogin usuario){
        Usuario u=repositorio.findByEmail(usuario.getEmail());
        if(u!=null){
            try{
                String password=desencriptar(u.getPassword());
                if(usuario.getPassword().equals(password)){
                    UsuarioAcces user=usuarioAccessConverter.EntitytoDTO(u);
                    return user;
                }else{
                    return null;
                }
            }catch(Exception ex){
                throw new Exceptions("InternalServerError", HttpStatus.INTERNAL_SERVER_ERROR);
            }
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
}
