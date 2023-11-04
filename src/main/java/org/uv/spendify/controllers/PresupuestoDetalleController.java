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
import org.uv.spendify.DTOs.presupuestos_det.DetalleNuevo;
import org.uv.spendify.DTOs.presupuestos_det.DetalleRegistrado;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.services.PresupuestoDetalleService;

/**
 *
 * @author juan
 */
@RestController
@CrossOrigin(origins="*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/budgetDetails")
public class PresupuestoDetalleController {
    private final PresupuestoDetalleService detalleService;
    
    public PresupuestoDetalleController(PresupuestoDetalleService detalleService){
        this.detalleService=detalleService;
    }
    
    @PostMapping("/addDetail/{id}")
    public ResponseEntity<DetalleRegistrado> addDetailByBudget(@PathVariable("id") long id, @RequestBody DetalleNuevo nuevo){
        DetalleRegistrado dr=detalleService.addDetailByBudget(nuevo, id);
        if(dr!=null){
            URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dr.getIdPresupuesto()).toUri();
            return ResponseEntity.created(ubication).body(dr);
        }else{
            throw new Exceptions("Budget not found.", HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/deleteDetail/{id}")
    public ResponseEntity<Void> deleteDetail(@PathVariable("id") long id){
        if(detalleService.deleteDetail(id)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/updateDetail/{id}")
    public ResponseEntity<Void> updateDetail(@PathVariable("id") long id, @RequestBody DetalleNuevo nuevo){
        if(detalleService.updateDetail(nuevo, id)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/getDetail/{id}")
    public ResponseEntity<DetalleRegistrado> getDetailById(@PathVariable("id") long id){
        DetalleRegistrado dr=detalleService.getDetail(id);
        if(dr!=null){
            return ResponseEntity.ok(dr);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
    
    @GetMapping("/getDetailsByBudget/{id}")
    public ResponseEntity<List<DetalleRegistrado>> getDetailsByBudget(@PathVariable("id") long id){
        List<DetalleRegistrado> lista=detalleService.getAllDetailsByBudget(id);
        if(lista!=null){
            return ResponseEntity.ok(lista);
        }else{
            throw new Exceptions("Budget not found.", HttpStatus.NOT_FOUND);
        }
    }
}