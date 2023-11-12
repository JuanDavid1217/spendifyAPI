/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.models;

import java.io.Serializable;
import java.time.LocalDateTime;
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
@Table(name="tokens")
public class Token implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tokens_id_token_seq")
    @SequenceGenerator(name="tokens_id_token_seq", sequenceName="tokens_id_token_seq", initialValue=1, allocationSize=1)
    @Column(name="id_token")
    private long idToken;
    
    @OneToOne()
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    
    @Column(name="fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column()
    private String clave;
    
    public long getIdToken(){
        return idToken;
    }
    
    public void setIdToken(long idToken){
        this.idToken=idToken;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    
}
