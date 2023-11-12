/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.dtos.ingresos;

/**
 *
 * @author juan
 */
public class IngresoNuevo extends IngresoBase{
    
    public IngresoNuevo(){
        super();
    }
    
    private long idUsuario;

    public long getId_usuario() {
        return idUsuario;
    }

    public void setId_usuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
}
