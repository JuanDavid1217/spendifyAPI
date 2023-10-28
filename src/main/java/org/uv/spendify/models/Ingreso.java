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
@Table(name="ingresos")
public class Ingreso implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ingresos_id_ingreso_seq")
    @SequenceGenerator(name="ingresos_id_ingreso_seq", sequenceName="ingresos_id_ingreso_seq", initialValue=1, allocationSize=1)
    @Column(name="id_ingreso")
    private long idIngreso;
    
    @ManyToOne()
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    
    @Column()
    private Date fecha;
    
    @Column()
    private BigDecimal monto;
    
    @Column()
    private String descripcion;
    
    @ManyToOne()
    @JoinColumn(name="id_tipo")
    private TipoIngreso tipo;

    public long getIdIngreso() {
        return idIngreso;
    }

    public void setIdIngreso(long idIngreso) {
        this.idIngreso = idIngreso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public TipoIngreso getTipo() {
        return tipo;
    }

    public void setTipo(TipoIngreso tipo) {
        this.tipo = tipo;
    }
    
    
}
