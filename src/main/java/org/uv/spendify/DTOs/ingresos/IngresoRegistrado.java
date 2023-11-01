/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.DTOs.ingresos;

/**
 *
 * @author juan
 */
public class IngresoRegistrado extends NuevoIngreso{
    public IngresoRegistrado(){
        super();
    }
    
    private long id_ingreso;

    public long getId_ingreso() {
        return id_ingreso;
    }

    public void setId_ingreso(long id_ingreso) {
        this.id_ingreso = id_ingreso;
    }
    
}
