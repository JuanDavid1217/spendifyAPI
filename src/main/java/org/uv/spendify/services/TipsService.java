/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.uv.spendify.models.Tip;
import org.uv.spendify.repository.TipsRepository;

/**
 *
 * @author juan
 */
@Service
public class TipsService {
    private final TipsRepository tipRepository;
    
    public TipsService(TipsRepository tipRepository){
        this.tipRepository=tipRepository;
    }
    
    public List<Tip> findAll(){
        return tipRepository.findAll();
    }
    
    public Tip findById(long id){
        Optional<Tip> tipo=tipRepository.findById(id);
        if(!tipo.isEmpty()){
            return tipo.get();
        }else{
            return null;
        }
    }
}
