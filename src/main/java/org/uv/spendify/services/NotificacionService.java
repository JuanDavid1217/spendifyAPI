/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.spendify.DTOs.notificaciones.NotificacionNueva;
import org.uv.spendify.DTOs.notificaciones.NotificacionRegistrada;
import org.uv.spendify.converters.notificaciones.NotificacionNuevaConverter;
import org.uv.spendify.converters.notificaciones.NotificacionRegistradaConverter;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.models.Notificacion;
import org.uv.spendify.models.PresupuestoDetalle;
import org.uv.spendify.repository.NotificacionRepository;
import org.uv.spendify.repository.PresupuestoDetalleRepository;
import static org.uv.spendify.validaciones.Validacion.umbralValidation;

/**
 *
 * @author juan
 */
@Service
public class NotificacionService {
    private final NotificacionNuevaConverter notificacionNuevaConverter;
    private final NotificacionRegistradaConverter notificacionRegistradaConverter;
    private final PresupuestoDetalleRepository detalleRepository;
    private final NotificacionRepository notificacionRepository;
    private final String mensaje="This Detail has not a notification registered yet.";
    public NotificacionService(NotificacionNuevaConverter notificacionNuevaConverter,
            NotificacionRegistradaConverter notificacionRegistradaConverter,
            PresupuestoDetalleRepository detalleRepository,
            NotificacionRepository notificacionRepository){
        this.notificacionNuevaConverter=notificacionNuevaConverter;
        this.notificacionRegistradaConverter=notificacionRegistradaConverter;
        this.detalleRepository=detalleRepository;
        this.notificacionRepository=notificacionRepository;
    }
    
    public NotificacionRegistrada addNotificacion(NotificacionNueva nueva, long id){
        Optional<PresupuestoDetalle> pd=detalleRepository.findById(id);
        if(!pd.isEmpty()){
            PresupuestoDetalle presupuestoDetalle=pd.get();
            if(presupuestoDetalle.getNotificacion()==null){
                if(umbralValidation(nueva.getUmbral())){
                    nueva.setIdDetalle(id);
                    Notificacion notificacion=notificacionNuevaConverter.DTOtoEntity(nueva);
                    notificacion=notificacionRepository.save(notificacion);
                    return notificacionRegistradaConverter.EntitytoDTO(notificacion);
                }else{
                    throw new Exceptions("Umbral out of range..", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("This Detail already has a notification registered.", HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public NotificacionRegistrada getNotificacion(long id){
        Optional<PresupuestoDetalle> pd=detalleRepository.findById(id);
        if(!pd.isEmpty()){
            PresupuestoDetalle presupuestoDetalle=pd.get();
            if(presupuestoDetalle.getNotificacion()!=null){
                Notificacion n=presupuestoDetalle.getNotificacion();
                return notificacionRegistradaConverter.EntitytoDTO(n);
            }else{
                throw new Exceptions(mensaje, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean updateNotificacion(NotificacionNueva nueva, long id){
        Optional<PresupuestoDetalle> pd=detalleRepository.findById(id);
        if(!pd.isEmpty()){
            PresupuestoDetalle presupuestoDetalle=pd.get();
            if(presupuestoDetalle.getNotificacion()!=null){
                if(umbralValidation(nueva.getUmbral())){
                    Notificacion notificacion=presupuestoDetalle.getNotificacion();
                    notificacion.setDescripcion(nueva.getDescripcion());
                    notificacion.setUmbral(nueva.getUmbral());
                    notificacionRepository.save(notificacion);
                    return true;
                }else{
                    throw new Exceptions("Umbral out of range..", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(mensaje, HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public boolean deleteNotificacion(long id){
        Optional<PresupuestoDetalle> pd=detalleRepository.findById(id);
        if(!pd.isEmpty()){
            PresupuestoDetalle presupuestoDetalle=pd.get();
            if(presupuestoDetalle.getNotificacion()!=null){
                Notificacion n=presupuestoDetalle.getNotificacion();
                notificacionRepository.delete(n);
                return true;
            }else{
                throw new Exceptions(mensaje, HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
}
