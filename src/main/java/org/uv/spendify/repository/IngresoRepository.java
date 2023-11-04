/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.spendify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.spendify.models.Ingreso;

/**
 *
 * @author juan
 */
public interface IngresoRepository extends JpaRepository<Ingreso, Long>{//para convertir tipos directos en sentencias sql se utiliza cast(campo as tipo)

    
}
