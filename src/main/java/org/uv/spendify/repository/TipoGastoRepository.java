/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.spendify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.spendify.models.TipoGasto;

/**
 *
 * @author juan
 */
public interface TipoGastoRepository extends JpaRepository<TipoGasto, Long>{
    
}
