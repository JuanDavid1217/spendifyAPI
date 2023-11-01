/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.uv.spendify.models.TipoIngreso;
import org.uv.spendify.repository.TipoIngresoRepository;

/**
 *
 * @author juan
 */
@Service
public class TipoIngresoService {
    private final TipoIngresoRepository repositorio;
    
    public TipoIngresoService(TipoIngresoRepository repositorio){
        this.repositorio=repositorio;
    }
    
    public List<TipoIngreso> findAll(){
        return repositorio.findAll();
    }
    
    public TipoIngreso findById(long id){
        Optional<TipoIngreso> tipo=repositorio.findById(id);
        if(!tipo.isEmpty()){
            return tipo.get();
        }else{
            return null;
        }
    }
}
