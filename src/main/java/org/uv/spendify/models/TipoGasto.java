/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 *
 * @author juan
 */
@Entity
@Table(name="tipo_gastos")
public class TipoGasto implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tipo_gastos_id_tipo_seq")
    @SequenceGenerator(name="tipo_gastos_id_tipo_seq", sequenceName="tipo_gastos_id_tipo_seq", initialValue=1, allocationSize=1)
    @Column(name="id_tipo")
    private long idTipoGasto;
    
    @Column()
    private String tipo;

    public long getIdTipoGasto() {
        return idTipoGasto;
    }

    public void setIdTipoGasto(long idTipoGasto) {
        this.idTipoGasto = idTipoGasto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
