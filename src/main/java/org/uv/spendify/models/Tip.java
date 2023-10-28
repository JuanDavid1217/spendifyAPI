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
@Table(name="tips_generales")
public class Tip implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tips_generales_id_tip_seq")
    @SequenceGenerator(name="tips_generales_id_tip_seq", sequenceName="tips_generales_id_tip_seq", initialValue=1, allocationSize=1)
    @Column(name="id_tip")
    private long idTip;
    
    @Column()
    private String descripcion;

    public long getIdTip() {
        return idTip;
    }

    public void setIdTip(long idTip) {
        this.idTip = idTip;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
