/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.controllers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.uv.spendify.dtos.tokens.TokenDTO;
import org.uv.spendify.dtos.usuarios.UsuarioBase;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.services.ForggotPasswordService;

/**
 *
 * @author juan
 */
@RestController()
@CrossOrigin(origins="*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/forggotPassword")
public class ForggotPasswordController {
    
    private final ForggotPasswordService service;
    
    public ForggotPasswordController(ForggotPasswordService service){
        this.service=service;
    }
    
    @PostMapping()
    public ResponseEntity<Void> sendKey(@RequestBody UsuarioBase email){
        if(service.sendKey(email)){
            return ResponseEntity.ok().build();
        }else{
            throw new Exceptions("Email not found.", HttpStatus.NOT_FOUND);
        }
        
    }
    
    @PostMapping("/verifyKey")
    public ResponseEntity<?> verifyKey(@RequestBody TokenDTO key){
        long id=service.validateKey(key);
        if(id>0){
            Map<String, Long> response = new HashMap<>();
            response.put("id_usuario", id);
            return ResponseEntity.ok(response);
        }else{
            throw new Exceptions("Invalid Key.", HttpStatus.UNAUTHORIZED);
        }
    }
}
