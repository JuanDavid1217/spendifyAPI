/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.uv.spendify.dtos.notificaciones.NotificacionNueva;
import org.uv.spendify.dtos.notificaciones.NotificacionRegistrada;
import org.uv.spendify.converters.notificaciones.NotificacionNuevaConverter;
import org.uv.spendify.converters.notificaciones.NotificacionRegistradaConverter;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.models.Notificacion;
import org.uv.spendify.models.PresupuestoDetalle;
import org.uv.spendify.repository.NotificacionRepository;
import org.uv.spendify.repository.PresupuestoDetalleRepository;
import static org.uv.spendify.validations.Validation.umbralValidation;

/**
 *
 * @author juan
 */
@Service
public class NotificacionService {
    private final NotificacionNuevaConverter notificacionNuevaConverter;
    private final NotificacionRegistradaConverter notificacionRegistradaConverter;
    private final NotificacionRepository notificacionRepository;
    private final PresupuestoDetalleRepository pdr;
    
    private String mensaje="This Detail has not a notification registered yet.";
    public NotificacionService(NotificacionNuevaConverter notificacionNuevaConverter,
            NotificacionRegistradaConverter notificacionRegistradaConverter,
            NotificacionRepository notificacionRepository,
            PresupuestoDetalleRepository pdr){
        this.notificacionNuevaConverter=notificacionNuevaConverter;
        this.notificacionRegistradaConverter=notificacionRegistradaConverter;
        this.notificacionRepository=notificacionRepository;
        this.pdr=pdr;
    }
    
    public NotificacionRegistrada addNotificacion(NotificacionNueva nueva, long id){
        NotificacionRegistrada notificacionRegistrada=null;
        Optional<PresupuestoDetalle> pd=pdr.findById(id);
        if(!pd.isEmpty()){
            PresupuestoDetalle presupuestoDetalle=pd.get();
            if(presupuestoDetalle.getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                if(presupuestoDetalle.getNotificacion()==null){
                    if(umbralValidation(nueva.getUmbral())){
                        nueva.setIdDetalle(presupuestoDetalle.getIdPresupuestoDetalle());
                        Notificacion notificacion=notificacionNuevaConverter.dtotoEntity(nueva);
                        notificacion=notificacionRepository.save(notificacion);
                        notificacionRegistrada= notificacionRegistradaConverter.entitytoDTO(notificacion);
                    }else{
                        throw new Exceptions("Umbral out of range..", HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions("This Detail already has a notification registered.", HttpStatus.CONFLICT);
                }
            }
        }
        return notificacionRegistrada;   
    }
    
    
    public NotificacionRegistrada getNotificacion(long id){
        NotificacionRegistrada notificacionRegistrada=null;
        Optional<PresupuestoDetalle> pd=pdr.findById(id);
        if(!pd.isEmpty()){
            PresupuestoDetalle presupuestoDetalle=pd.get();
            if(presupuestoDetalle.getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                if(presupuestoDetalle.getNotificacion()!=null){
                    Notificacion n=presupuestoDetalle.getNotificacion();
                    notificacionRegistrada=notificacionRegistradaConverter.entitytoDTO(n);
                }else{
                    throw new Exceptions(mensaje, HttpStatus.CONFLICT);
                }
            }
        }
        return notificacionRegistrada;
    }
    
    public boolean updateNotificacion(NotificacionNueva nueva, long id){
        boolean bandera=false;
        Optional<PresupuestoDetalle> pd=pdr.findById(id);
        if(!pd.isEmpty()){
            PresupuestoDetalle presupuestoDetalle=pd.get();
            if(presupuestoDetalle.getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                if(presupuestoDetalle.getNotificacion()!=null){
                    if(umbralValidation(nueva.getUmbral())){
                        Notificacion notificacion=presupuestoDetalle.getNotificacion();
                        notificacion.setDescripcion(nueva.getDescripcion());
                        notificacion.setUmbral(nueva.getUmbral());
                        notificacionRepository.save(notificacion);
                        bandera=true;
                    }else{
                        throw new Exceptions("Umbral out of range..", HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions(mensaje, HttpStatus.CONFLICT);
                }
            }
        }
        return bandera;
    }
    
    public boolean deleteNotificacion(long id){
        boolean bandera=false;
        Optional<PresupuestoDetalle> pd=pdr.findById(id);
        if(!pd.isEmpty()){
            PresupuestoDetalle presupuestoDetalle=pd.get();
            if(presupuestoDetalle.getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                if(presupuestoDetalle.getNotificacion()!=null){
                    Notificacion n=presupuestoDetalle.getNotificacion();
                    notificacionRepository.delete(n);
                    bandera=true;
                }else{
                    throw new Exceptions(mensaje, HttpStatus.CONFLICT);
                }
            }
        }
        return bandera;
    }
}
