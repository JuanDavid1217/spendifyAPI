/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.uv.spendify.models.TipoGasto;
import org.uv.spendify.repository.TipoGastoRepository;

/**
 *
 * @author juan
 */
@Service
public class TipoGastoService {
    
    private final TipoGastoRepository repositorio;
    
    public TipoGastoService(TipoGastoRepository repositorio){
        this.repositorio=repositorio;
    }
    
    public List<TipoGasto> findAll(){
        return repositorio.findAll();
    }
    
    public TipoGasto findById(long id){
        Optional<TipoGasto> tipo=repositorio.findById(id);
        if(!tipo.isEmpty()){
            return tipo.get();
        }else{
            return null;
        }
    }
}
