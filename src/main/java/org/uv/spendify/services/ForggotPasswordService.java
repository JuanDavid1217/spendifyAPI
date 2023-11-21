/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;
import org.uv.spendify.dtos.tokens.TokenDTO;
import org.uv.spendify.dtos.usuarios.UsuarioBase;
import org.uv.spendify.email.Email;
import org.uv.spendify.models.Token;
import org.uv.spendify.models.Usuario;
import org.uv.spendify.repository.TokenRepository;
import org.uv.spendify.repository.UsuarioRepository;
import static org.uv.spendify.validations.Validation.isTokenValid;

/**
 *
 * @author juan
 */
@Service
public class ForggotPasswordService {
    private final Email emailComponent;
    private final UsuarioRepository repository;
    private final TokenRepository tokenRepository;
    
    public ForggotPasswordService(Email emailComponent, UsuarioRepository repository,
            TokenRepository tokenRepository){
        this.emailComponent=emailComponent;
        this.repository=repository;
        this.tokenRepository=tokenRepository;
    }
    
    public boolean sendKey(UsuarioBase email){
        Usuario u=repository.findByEmail(email.getEmail());
        if(u!=null){
            boolean pase=true;
            while(pase){
                String key=this.generatedKey();
                /*Si es necesario implementa un ciclo*/
                if(tokenRepository.findByClave(key)==null){
                    Token t=tokenRepository.findByIdUsuario(u.getIdUsuario());
                    if(t!=null){
                        t.setClave(key);
                        t.setFechaCreacion(LocalDateTime.now());
                    }else{
                        t=new Token();
                        t.setClave(key);
                        t.setUsuario(u);
                        t.setFechaCreacion(LocalDateTime.now());
                    }
                    tokenRepository.save(t);
                    String message="Hola, "+u.getNombre()+" la siguiente clave es tu acceso para cambiar tu contraseña.\n"
                        +"No la compartas con Nadie\n\n"+key;
                    emailComponent.sendEmail(email.getEmail(), "Forggot Password", message);
                    pase=false;
                }    
            }
            return true;
        }else{
            return false;
        }
    }
    
    public long validateKey(TokenDTO key){
        Token t=tokenRepository.findByClave(key.getClave());
        if(t!=null && t.getClave().length()==6 && isTokenValid(t.getFechaCreacion())){
            return t.getUsuario().getIdUsuario();
        }else{
            return 0;
        }
    }
    
    private String generatedKey(){
        String banco="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        
        String key="";
        
        for (int i=0; i<6; i++){
            int indiceAleatorio = this.generatedNumberRamdom(0, banco.length());
            char randomCharacter = banco.charAt(indiceAleatorio);
            key+=randomCharacter;
        }
        
        return key;
    }
    
    private int generatedNumberRamdom(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
