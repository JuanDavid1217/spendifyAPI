/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.dtos.presupuestos_det;

/**
 *
 * @author juan
 */
public class DetalleRegistrado extends DetalleNuevo{
    private long idPresupuestoDetalle;
    private long idPresupuesto;
    
    public DetalleRegistrado(){
        super();
    }

    public long getIdPresupuestoDetalle() {
        return idPresupuestoDetalle;
    }

    public void setIdPresupuestoDetalle(long idPresupuestoDetalle) {
        this.idPresupuestoDetalle = idPresupuestoDetalle;
    }

    public long getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(long idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }
    
}
