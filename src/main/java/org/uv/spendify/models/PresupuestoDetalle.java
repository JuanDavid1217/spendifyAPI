/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.models;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/**
 *
 * @author juan
 */
@Entity
@Table(name="presupuesto_detalle")
public class PresupuestoDetalle implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="presupuesto_detalle_id_detalle_seq")
    @SequenceGenerator(name="presupuesto_detalle_id_detalle_seq", sequenceName="presupuesto_detalle_id_detalle_seq", initialValue=1, allocationSize=1)
    @Column(name="id_detalle")
    private long idPresupuestoDetalle;
    
    @ManyToOne()
    @JoinColumn(name="id_tipo")
    private TipoGasto tipo;
    
    @ManyToOne()
    @JoinColumn(name="id_presupuesto")
    private Presupuesto presupuesto;
    
    @Column()
    private BigDecimal monto;
    
    @OneToMany(mappedBy="detalle", cascade={CascadeType.REMOVE/*, CascadeType.MERGE*/})
    private List<Gasto> gastos;
    
    @OneToOne(mappedBy="detalle", cascade={CascadeType.REMOVE/*, CascadeType.MERGE*/})
    private Notificacion notificacion;

    public long getIdPresupuestoDetalle() {
        return idPresupuestoDetalle;
    }

    public void setIdPresupuestoDetalle(long idPresupuestoDetalle) {
        this.idPresupuestoDetalle = idPresupuestoDetalle;
    }

    public TipoGasto getTipo() {
        return tipo;
    }

    public void setTipo(TipoGasto tipo) {
        this.tipo = tipo;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public List<Gasto> getGastos() {
        return gastos;
    }

    public void setGastos(List<Gasto> gastos) {
        this.gastos = gastos;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }
    
    
}
