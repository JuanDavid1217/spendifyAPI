/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.DTOs.ingresos;

/**
 *
 * @author juan
 */
public class IngresoNuevo extends IngresoBase{
    
    public IngresoNuevo(){
        super();
    }
    
    private long id_usuario;

    public long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(long id_usuario) {
        this.id_usuario = id_usuario;
    }
    
}
