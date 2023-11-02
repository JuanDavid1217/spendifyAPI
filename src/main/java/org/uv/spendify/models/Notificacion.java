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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author juan
 */
@Entity
@Table(name="notificaciones")
public class Notificacion implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="notificaciones_id_notificacion_seq")
    @SequenceGenerator(name="notificaciones_id_notificacion_seq", sequenceName="notificaciones_id_notificacion_seq", initialValue=1, allocationSize=1)
    @Column(name="id_notificacion")
    private long idNotificacion;
    
    @OneToOne()
    @JoinColumn(name="id_detalle")
    private PresupuestoDetalle detalle;
    
    @Column()
    private String descripcion;
    
    @Column()
    private double umbral;

    public long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public PresupuestoDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(PresupuestoDetalle detalle) {
        this.detalle = detalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getUmbral() {
        return umbral;
    }

    public void setUmbral(double umbral) {
        this.umbral = umbral;
    }
    
    
}
