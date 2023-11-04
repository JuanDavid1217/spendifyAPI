/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.DTOs.notificaciones;

/**
 *
 * @author juan
 */
public class NotificacionRegistrada extends NotificacionNueva{
    private long idNotificacion;
    
    public NotificacionRegistrada(){
        super();
    }

    public long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }
    
}
