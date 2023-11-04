/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author juan
 */
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="usuarios_id_usuario_seq")
    @SequenceGenerator(name="usuarios_id_usuario_seq", sequenceName="usuarios_id_usuario_seq", initialValue=1, allocationSize=1)
    @Column(name="id_usuario")
    private long idUsuario;
    
    @Column()
    private int edad;
    
    @Column()
    private String telefono;
    
    @Column()
    private String email;
    
    @Column()
    private String password;
    
    @Column()
    private String nombre;
    
    @Column(name="apellido_paterno")
    private String apellidoPaterno;
    
    @Column(name="apellido_Materno")
    private String apellidoMaterno;
    
    @OneToMany(mappedBy="usuario", cascade={CascadeType.REMOVE})
    private List<Presupuesto> presupuestos;
    
    @OneToMany(mappedBy="usuario", cascade={CascadeType.REMOVE})
    private List<Ingreso> ingresos;
    

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Presupuesto> getPresupuestos() {
        return presupuestos;
    }

    public void setPresupuestos(List<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    public List<Ingreso> getIngresos() {
        return ingresos;
    }

    public void setIngresos(List<Ingreso> ingresos) {
        this.ingresos = ingresos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    

    
    
}
