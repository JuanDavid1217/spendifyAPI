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
@Table(name="tipo_ingresos")
public class TipoIngreso implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tipo_ingresos_id_tipo_seq")
    @SequenceGenerator(name="tipo_ingresos_id_tipo_seq", sequenceName="tipo_ingresos_id_tipo_seq", initialValue=1, allocationSize=1)
    @Column(name="id_tipo")
    private Long idTipoIngreso;
    
    @Column()
    private String tipo;

    public Long getIdTipoIngreso() {
        return idTipoIngreso;
    }

    public void setIdTipoIngreso(Long idTipoIngreso) {
        this.idTipoIngreso = idTipoIngreso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
}
