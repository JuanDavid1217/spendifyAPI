/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 *
 * @author juan
 */
@Entity
@Table(name="gastos")
public class Gasto implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="gastos_id_gastos_seq")
    @SequenceGenerator(name="gastos_id_gastos_seq", sequenceName="gastos_id_gastos_seq", initialValue=1, allocationSize=1)
    @Column(name="id_gastos")
    private long idGasto;
    
    @ManyToOne()
    @JoinColumn(name="id_detalle")
    private PresupuestoDetalle detalle;
    
    @Column()
    private Date fecha;
    
    @Column()
    private BigDecimal monto;
    
    @Column()
    private String descripcion;

    public long getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(long idGasto) {
        this.idGasto = idGasto;
    }

    public PresupuestoDetalle getDetalle() {
        return detalle;
    }

    public void setDetalle(PresupuestoDetalle detalle) {
        this.detalle = detalle;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
