/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.dtos.usuarios;

/**
 *
 * @author juan
 */
public class UsuarioPassword extends UsuarioPasswordBase{
    private String oldPasswaord;
    
    public UsuarioPassword(){
        super();
    }

    public String getOldPasswaord() {
        return oldPasswaord;
    }

    public void setOldPasswaord(String oldPasswaord) {
        this.oldPasswaord = oldPasswaord;
    }
    
    
}
