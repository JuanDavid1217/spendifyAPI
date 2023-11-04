/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.DTOs.presupuestos;

import java.math.BigDecimal;
import java.util.List;
import org.uv.spendify.DTOs.presupuestos_det.DetalleNuevo;

/**
 *
 * @author juan
 */
public class PresupuestoNuevo extends PresupuestoBase{
    
    private List<DetalleNuevo> detalles;
    
    public PresupuestoNuevo(){
        super();
    }

    public List<DetalleNuevo> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleNuevo> detalles) {
        this.detalles = detalles;
    }
    
    
}
