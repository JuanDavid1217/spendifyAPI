/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.presupuesto;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.DTOs.presupuestos.PresupuestoNuevo;
import org.uv.spendify.DTOs.presupuestos_det.DetalleNuevo;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.converters.presupuesto_det.NuevoDetalleConverter;
import org.uv.spendify.models.Presupuesto;
import org.uv.spendify.models.PresupuestoDetalle;
import org.uv.spendify.models.Usuario;
import static org.uv.spendify.validaciones.Validacion.StringtoDate;
import static org.uv.spendify.validaciones.Validacion.DatetoSring;

/**
 *
 * @author juan
 */
@Component
public class NuevoPresupuestoConverter implements Converter<Presupuesto, PresupuestoNuevo>{
    private final NuevoDetalleConverter detalleConverter;
    
    public NuevoPresupuestoConverter(NuevoDetalleConverter detalleConverter){
        this.detalleConverter=detalleConverter;
    }
    
    @Override
    public Presupuesto DTOtoEntity(PresupuestoNuevo dto) {
        Presupuesto nuevo=new Presupuesto();
        List<PresupuestoDetalle> detalles=detalleConverter.DTOListtoEntityList(dto.getDetalles());
        nuevo.setDetalles(detalles);
        nuevo.setFechaInicio(new java.sql.Date(StringtoDate(dto.getFechaInicio()).getTime()));
        nuevo.setFechaFin(new java.sql.Date(StringtoDate(dto.getFechaFin()).getTime()));
        nuevo.setMontoTotal(dto.getMontoTotal());
        Usuario u=new Usuario();
        u.setIdUsuario(dto.getIdUsuario());
        nuevo.setUsuario(u);
        return nuevo;
    }

    @Override
    public PresupuestoNuevo EntitytoDTO(Presupuesto entity) {
        PresupuestoNuevo nuevo=new PresupuestoNuevo();
        List<DetalleNuevo> detalles=detalleConverter.EntityListtoDTOList(entity.getDetalles());
        nuevo.setDetalles(detalles);
        nuevo.setFechaFin(DatetoSring(entity.getFechaFin()));
        nuevo.setFechaInicio(DatetoSring(entity.getFechaInicio()));
        nuevo.setIdUsuario(entity.getUsuario().getIdUsuario());
        nuevo.setMontoTotal(entity.getMontoTotal());
        return nuevo;
    }

    @Override
    public List<Presupuesto> DTOListtoEntityList(List<PresupuestoNuevo> dtoList) {
      return dtoList.stream().map(this::DTOtoEntity).collect(Collectors.toList());
    }

    @Override
    public List<PresupuestoNuevo> EntityListtoDTOList(List<Presupuesto> entityList) {
        return entityList.stream().map(this::EntitytoDTO).collect(Collectors.toList());
    }
    
}
