/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 *
 * @author juan
 */
@Entity
@Table(name="presupuesto")
public class Presupuesto implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="presupuesto_id_presupuesto_seq")
    @SequenceGenerator(name="presupuesto_id_presupuesto_seq", sequenceName="presupuesto_id_presupuesto_seq", initialValue=1, allocationSize=1)
    @Column(name="id_presupuesto")
    private long idPresupuesto;
    
    @ManyToOne()
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    
    @Column()
    private Date fechaInicio;
    
    @Column()
    private Date fechaFin;
    
    @Column()
    private BigDecimal montoTotal;
    
    @OneToMany(mappedBy="presupuesto", cascade={CascadeType.REMOVE, CascadeType.MERGE})
    private List<PresupuestoDetalle> detalles; 

    public long getIdPresupuesto() {
        return idPresupuesto;
    }

    public void setIdPresupuesto(long idPresupuesto) {
        this.idPresupuesto = idPresupuesto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }

    public List<PresupuestoDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<PresupuestoDetalle> detalles) {
        this.detalles = detalles;
    }
    
    
}
