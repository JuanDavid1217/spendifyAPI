/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.controllers;

import java.math.BigDecimal;
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
import org.uv.spendify.dtos.gastos.GastoNuevo;
import org.uv.spendify.dtos.gastos.GastoRegistrado;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.services.GastoService;

/**
 *
 * @author juan
 */
@RestController
@CrossOrigin(origins="*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/expenses")
public class GastoController {
    private final GastoService gastoService;
    
    public GastoController(GastoService gastoService){
        this.gastoService=gastoService;
    }
    
    @PostMapping("/addExpense")
    public ResponseEntity<GastoRegistrado> addExpense(@RequestBody GastoNuevo nuevo){
        GastoRegistrado gr=gastoService.saveGasto(nuevo);
        if(gr!=null){
            URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(gr.getIdGasto()).toUri();
        
        return ResponseEntity.created(ubication).body(gr);
        }else{
            throw new Exceptions("Detail not found.", HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/updateExpense/{id}")
    public ResponseEntity<Void> updateExpense(@PathVariable("id") long id, @RequestBody GastoNuevo nuevo){
        if(gastoService.updateGasto(nuevo, id)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/getExpense/{id}")
    public ResponseEntity<GastoRegistrado> getExpenseById(@PathVariable("id") long id){
        GastoRegistrado gr=gastoService.getGasto(id);
        if(gr!=null){
            return ResponseEntity.ok(gr);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/getAllExpenseByDetail/{id}")
    public ResponseEntity<List<GastoRegistrado>> getAllExpenseByDetail(@PathVariable("id") long id){
        List<GastoRegistrado> gr=gastoService.getAllGastoByDetail(id);
        if(gr!=null){
            return ResponseEntity.ok(gr);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/deleteExpense/{id}")
    public ResponseEntity<Void> deleteExpenseById(@PathVariable("id") long id){
        if(gastoService.deleteGasto(id)){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/deleteAllExpenseByDetail/{id}")
    public ResponseEntity<Void> deleteAllExpenseByDetail(@PathVariable("id") long id){
        if(gastoService.deleteAllGastoByDetail(id)){
            return ResponseEntity.noContent().build();
        }else{
            throw new Exceptions("Detail not found.", HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/")
    public ResponseEntity<BigDecimal> sumOfGastosByUser(){
        BigDecimal gastos=gastoService.sumOfGastosByUser();
        return ResponseEntity.ok().body(gastos);
    }
    
    @GetMapping("/getAllExpensesByUser")
    public ResponseEntity<List<GastoRegistrado>> getAllExpensesByUser(){
        List<GastoRegistrado> gr=gastoService.getAllExpensesByUser();
        return ResponseEntity.ok(gr);
    }
}
