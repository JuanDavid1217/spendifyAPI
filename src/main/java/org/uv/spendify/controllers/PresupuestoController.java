/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.controllers;

import java.net.URI;
import java.util.List;
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
import org.uv.spendify.dtos.presupuestos.PresupuestoNuevo;
import org.uv.spendify.dtos.presupuestos.PresupuestoBase;
import org.uv.spendify.dtos.presupuestos.PresupuestoRegistrado;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.services.PresupuestoService;

/**
 *
 * @author juan
 */
@RestController
@CrossOrigin(origins="*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/budgets")
public class PresupuestoController {
    private PresupuestoService service;
    
    public PresupuestoController(PresupuestoService service){
        this.service=service;
    }
    
    @PostMapping("/saveBudget")
    public ResponseEntity<PresupuestoRegistrado> saveBudget(@RequestBody PresupuestoNuevo newBudget){
        PresupuestoRegistrado registeredBudget=service.savePresupuesto(newBudget);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(registeredBudget.getIdPresupuesto()).toUri();
        
        return ResponseEntity.created(ubication).body(registeredBudget);
    }
    
    @GetMapping("/getBudget/{id}")
    public ResponseEntity<PresupuestoRegistrado> getButgetById(@PathVariable("id") long id){
        PresupuestoRegistrado p=service.getBudgetById(id);
        if(p!=null){
            return ResponseEntity.ok(p);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/getAllBudgesByUser")
    public ResponseEntity<List<PresupuestoRegistrado>> getAllButgetByUser(){
        List<PresupuestoRegistrado> p=service.getAllBudgetByUser();
        if(p!=null){
            return ResponseEntity.ok(p);
        }else{
            throw new Exceptions("User not found.", HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/deleteBudget/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable("id") long id){
        if(service.deletePresupuesto(id)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/updateBudget/{id}")
    public ResponseEntity<Void> updateBudget(@PathVariable("id") long id, @RequestBody PresupuestoBase nuevo){
        if(service.updatePresupuesto(nuevo, id)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("deleteAllBudgetByUser")
    public ResponseEntity<Void> deleteAllBudgetByUser(){
        if(service.deleteAllPresupuestoByUser()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
