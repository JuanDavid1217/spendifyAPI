/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.DTOs.presupuestos;

import java.util.List;
import org.uv.spendify.DTOs.presupuestos_det.DetalleRegistrado;

/**
 *
 * @author juan
 */
public class PresupuestoRegistrado extends PresupuestoBase{
    private long idPresupuesto;
    private List<DetalleRegistrado> detalles;
    
    public PresupuestoRegistrado(){
        super();
    }

    public long getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(long idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public List<DetalleRegistrado> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleRegistrado> detalles) {
        this.detalles = detalles;
    }
    
    
    
}
