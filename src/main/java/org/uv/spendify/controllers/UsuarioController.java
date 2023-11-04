/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.controllers;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.uv.spendify.DTOs.usuarios.UsuarioAcces;
import org.uv.spendify.DTOs.usuarios.UsuarioLogin;
import org.uv.spendify.DTOs.usuarios.UsuarioNewAccount;
import org.uv.spendify.DTOs.usuarios.UsuarioPassword;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.services.UsuarioService;

/**
 *
 * @author juan
 */
@RestController
@CrossOrigin(origins="*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/users")
public class UsuarioController {
    private final UsuarioService service;
    
    public UsuarioController(UsuarioService service){
        this.service=service;
    }
    
    @PostMapping("/createAccount")
    public ResponseEntity<UsuarioAcces> createAccount(@RequestBody UsuarioNewAccount newUser){
        UsuarioAcces usuario=service.createAccount(newUser);
        if(usuario!=null){
            
            URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(usuario.getId()).toUri();
        
            return ResponseEntity.created(ubication).body(usuario);
            
        }else{
            throw new Exceptions("Email register already", HttpStatus.CONFLICT);
        }
    }
    
    @PutMapping("/updateAccount/{id}")
    public ResponseEntity<Void> updateAccount(@PathVariable("id") long id,@RequestBody UsuarioAcces usuario){
        UsuarioAcces u=service.updateAccount(usuario, id);
        if(u==null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    
    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable long id){
        boolean response=service.deleteAccount(id);
        if(response==false){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    
    @PutMapping("/changePassword/{id}")
    public ResponseEntity<Void> changePassword(@PathVariable("id") long id, @RequestBody UsuarioPassword passwords){
        boolean response=service.changePassword(passwords, id);
        if(response==false){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<UsuarioAcces> login(@RequestBody UsuarioLogin usuario){
        UsuarioAcces acceso=service.login(usuario);
        if(acceso!=null){
            return ResponseEntity.ok(acceso);
        }else{
            throw new Exceptions("Email or Password are incorrect.", HttpStatus.NOT_FOUND);
        }
    }
}
