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
import org.uv.spendify.dtos.notificaciones.NotificacionNueva;
import org.uv.spendify.dtos.notificaciones.NotificacionRegistrada;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.services.NotificacionService;

/**
 *
 * @author juan
 */
@RestController
@CrossOrigin(origins="*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/notificationsByBudgetDetail")
public class NotificacionController {
    private final NotificacionService service;
    private String message="Detail Not Found";
    
    public NotificacionController(NotificacionService service){
        this.service=service;
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<NotificacionRegistrada> addNotificacion(@PathVariable("id") long id, @RequestBody NotificacionNueva nueva){
        NotificacionRegistrada notificacion=service.addNotificacion(nueva, id);
        if(notificacion!=null){
            URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(notificacion.getIdNotificacion()).toUri();
        
        return ResponseEntity.created(ubication).body(notificacion);
        }else{
            throw new Exceptions(message, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionRegistrada> getNotificacion(@PathVariable("id") long id){
        NotificacionRegistrada notificacion=service.getNotificacion(id);
        if(notificacion!=null){
            return ResponseEntity.ok(notificacion);
        }else{
            throw new Exceptions(message, HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNotificacion(@PathVariable("id") long id, @RequestBody NotificacionNueva nueva){
        boolean respuesta=service.updateNotificacion(nueva, id);
        if(respuesta){
            return ResponseEntity.noContent().build();
        }else{
            throw new Exceptions(message, HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable("id") long id){
        boolean respuesta=service.deleteNotificacion(id);
        if(respuesta){
            return ResponseEntity.noContent().build();
        }else{
            throw new Exceptions(message, HttpStatus.NOT_FOUND);
        }
    }
}
