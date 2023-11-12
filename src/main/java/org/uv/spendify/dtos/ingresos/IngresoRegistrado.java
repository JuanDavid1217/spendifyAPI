/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.dtos.ingresos;

/**
 *
 * @author juan
 */
public class IngresoRegistrado extends IngresoNuevo{
    public IngresoRegistrado(){
        super();
    }
    
    private long idIngreso;

    public long getId_ingreso() {
        return idIngreso;
    }

    public void setId_ingreso(long idIngreso) {
        this.idIngreso = idIngreso;
    }
    
}
