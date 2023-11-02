/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.DTOs.gastos;

/**
 *
 * @author juan
 */
public class GastoRegistrado extends GastoNuevo{
    private long idGasto;
    
    public GastoRegistrado(){
        super();
    }

    public long getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(long idGasto) {
        this.idGasto = idGasto;
    }
}
