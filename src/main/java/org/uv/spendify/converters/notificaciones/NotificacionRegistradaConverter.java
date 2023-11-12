/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.notificaciones;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.dtos.notificaciones.NotificacionRegistrada;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.Notificacion;
import org.uv.spendify.models.PresupuestoDetalle;

/**
 *
 * @author juan
 */
@Component
public class NotificacionRegistradaConverter implements Converter<Notificacion, NotificacionRegistrada>{

    @Override
    public Notificacion dtotoEntity(NotificacionRegistrada dto) {
        Notificacion nueva=new Notificacion();
        nueva.setDescripcion(dto.getDescripcion());
        PresupuestoDetalle detalle=new PresupuestoDetalle();
        detalle.setIdPresupuestoDetalle(dto.getIdDetalle());
        nueva.setDetalle(detalle);
        nueva.setIdNotificacion(dto.getIdNotificacion());
        nueva.setUmbral(dto.getUmbral());
        return nueva;
    }

    @Override
    public NotificacionRegistrada entitytoDTO(Notificacion entity) {
        NotificacionRegistrada nueva=new NotificacionRegistrada();
        nueva.setDescripcion(entity.getDescripcion());
        nueva.setIdDetalle(entity.getDetalle().getIdPresupuestoDetalle());
        nueva.setIdNotificacion(entity.getIdNotificacion());
        nueva.setUmbral(entity.getUmbral());
        return nueva;
    }

    @Override
    public List<Notificacion> dtoListtoEntityList(List<NotificacionRegistrada> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());    }

    @Override
    public List<NotificacionRegistrada> entityListtoDTOList(List<Notificacion> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
