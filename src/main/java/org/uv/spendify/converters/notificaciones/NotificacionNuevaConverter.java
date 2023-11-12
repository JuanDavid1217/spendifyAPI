/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.notificaciones;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.dtos.notificaciones.NotificacionNueva;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.Notificacion;
import org.uv.spendify.models.PresupuestoDetalle;

/**
 *
 * @author juan
 */
@Component
public class NotificacionNuevaConverter implements Converter<Notificacion, NotificacionNueva>{

    @Override
    public Notificacion dtotoEntity(NotificacionNueva dto) {
        Notificacion nueva=new Notificacion();
        nueva.setDescripcion(dto.getDescripcion());
        PresupuestoDetalle detalle=new PresupuestoDetalle();
        detalle.setIdPresupuestoDetalle(dto.getIdDetalle());
        nueva.setDetalle(detalle);
        nueva.setUmbral(dto.getUmbral());
        return nueva;
    }

    @Override
    public NotificacionNueva entitytoDTO(Notificacion entity) {
        NotificacionNueva nueva=new NotificacionNueva();
        nueva.setDescripcion(entity.getDescripcion());
        nueva.setIdDetalle(entity.getDetalle().getIdPresupuestoDetalle());
        nueva.setUmbral(entity.getUmbral());
        return nueva;
    }

    @Override
    public List<Notificacion> dtoListtoEntityList(List<NotificacionNueva> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }

    @Override
    public List<NotificacionNueva> entityListtoDTOList(List<Notificacion> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
