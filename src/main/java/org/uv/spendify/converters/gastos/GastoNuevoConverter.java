/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.gastos;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.DTOs.gastos.GastoNuevo;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.Gasto;
import org.uv.spendify.models.PresupuestoDetalle;
import static org.uv.spendify.validaciones.Validacion.DatetoSring;
import static org.uv.spendify.validaciones.Validacion.StringtoDate;

/**
 *
 * @author juan
 */
@Component
public class GastoNuevoConverter implements Converter<Gasto, GastoNuevo>{

    @Override
    public Gasto DTOtoEntity(GastoNuevo dto) {
        Gasto nuevo=new Gasto();
        nuevo.setDescripcion(dto.getDescripcion());
        PresupuestoDetalle detalle=new PresupuestoDetalle();
        detalle.setIdPresupuestoDetalle(dto.getIdDetalle());
        nuevo.setDetalle(detalle);
        nuevo.setFecha(new java.sql.Date(StringtoDate(dto.getFecha()).getTime()));
        nuevo.setMonto(dto.getMonto());
        return nuevo;
    }

    @Override
    public GastoNuevo EntitytoDTO(Gasto entity) {
        GastoNuevo nuevo=new GastoNuevo();
        nuevo.setDescripcion(entity.getDescripcion());
        nuevo.setFecha(DatetoSring(entity.getFecha()));
        nuevo.setIdDetalle(entity.getDetalle().getIdPresupuestoDetalle());
        nuevo.setMonto(entity.getMonto());
        return nuevo;
    }

    @Override
    public List<Gasto> DTOListtoEntityList(List<GastoNuevo> dtoList) {
        return dtoList.stream().map(this::DTOtoEntity).collect(Collectors.toList());
    }

    @Override
    public List<GastoNuevo> EntityListtoDTOList(List<Gasto> entityList) {
        return entityList.stream().map(this::EntitytoDTO).collect(Collectors.toList());
    }
    
}
