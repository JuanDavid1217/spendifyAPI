/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.uv.spendify.models.TipoGasto;
import org.uv.spendify.services.TipoGastoService;

/**
 *
 * @author juan
 */
@RestController
@CrossOrigin(origins="*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/tipoGastos")
public class TipoGastoController {
    private final TipoGastoService service;
    
    public TipoGastoController(TipoGastoService service){
        this.service=service;
    }
    
    @GetMapping()
    public ResponseEntity<List<TipoGasto>> getAllExpensesTypes(){
        List<TipoGasto> tipos=service.findAll();
        return ResponseEntity.ok(tipos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TipoGasto> getExpenseTypeById(@PathVariable("id") long id){
        TipoGasto tipo=service.findById(id);
        if(tipo!=null){
            return ResponseEntity.ok(tipo);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
