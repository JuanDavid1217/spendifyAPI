/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.presupuesto;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.dtos.presupuestos.PresupuestoRegistrado;
import org.uv.spendify.dtos.presupuestos_det.DetalleRegistrado;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.converters.presupuesto_det.DetalleRegistradoConverter;
import org.uv.spendify.models.Presupuesto;
import org.uv.spendify.models.PresupuestoDetalle;
import org.uv.spendify.models.Usuario;
import static org.uv.spendify.validations.Validation.stringtoDate;
import static org.uv.spendify.validations.Validation.datetoSring;


/**
 *
 * @author juan
 */
@Component
public class PresupuestoRegistradoConverter implements Converter<Presupuesto, PresupuestoRegistrado>{
    private final DetalleRegistradoConverter detalleConverter;
    
    public PresupuestoRegistradoConverter(DetalleRegistradoConverter detalleConverter){
        this.detalleConverter=detalleConverter;
    }
    
    @Override
    public Presupuesto dtotoEntity(PresupuestoRegistrado dto) {
        Presupuesto presupuesto=new Presupuesto();
        List<PresupuestoDetalle> detalles=detalleConverter.dtoListtoEntityList(dto.getDetalles());
        presupuesto.setDetalles(detalles);
        presupuesto.setFechaFin(new java.sql.Date(stringtoDate(dto.getFechaFin()).getTime()));
        presupuesto.setFechaInicio(new java.sql.Date(stringtoDate(dto.getFechaInicio()).getTime()));
        presupuesto.setIdPresupuesto(dto.getIdPresupuesto());
        presupuesto.setMontoTotal(dto.getMontoTotal());
        Usuario u=new Usuario();
        u.setIdUsuario(dto.getIdUsuario());
        presupuesto.setUsuario(u);
        return presupuesto;
    }

    @Override
    public PresupuestoRegistrado entitytoDTO(Presupuesto entity) {
        PresupuestoRegistrado presupuesto=new PresupuestoRegistrado();
        List<DetalleRegistrado> detalles=detalleConverter.entityListtoDTOList(entity.getDetalles());
        presupuesto.setDetalles(detalles);
        presupuesto.setFechaFin(datetoSring(entity.getFechaFin()));
        presupuesto.setFechaInicio(datetoSring(entity.getFechaInicio()));
        presupuesto.setIdPresupuesto(entity.getIdPresupuesto());
        presupuesto.setIdUsuario(entity.getUsuario().getIdUsuario());
        presupuesto.setMontoTotal(entity.getMontoTotal());
        return presupuesto;
    }

    @Override
    public List<Presupuesto> dtoListtoEntityList(List<PresupuestoRegistrado> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }

    @Override
    public List<PresupuestoRegistrado> entityListtoDTOList(List<Presupuesto> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
   
}
