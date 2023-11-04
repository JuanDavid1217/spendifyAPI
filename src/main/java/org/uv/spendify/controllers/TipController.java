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
import org.uv.spendify.models.Tip;
import org.uv.spendify.services.TipsService;

/**
 *
 * @author juan
 */
@RestController
@CrossOrigin(origins="*", methods={RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/tips")
public class TipController {
    private final TipsService service;
    
    public TipController(TipsService service){
        this.service=service;
    }
    
    @GetMapping()
    public ResponseEntity<List<Tip>> getAllTips(){
        List<Tip> tips=service.findAll();
        return ResponseEntity.ok(tips);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Tip> getTipsById(@PathVariable("id") long id){
        Tip tip=service.findById(id);
        if(tip!=null){
            return ResponseEntity.ok(tip);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
