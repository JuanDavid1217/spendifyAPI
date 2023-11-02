/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.gastos;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.DTOs.gastos.GastoRegistrado;
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
public class GastoRegistradoConverter implements Converter<Gasto, GastoRegistrado>{

    @Override
    public Gasto DTOtoEntity(GastoRegistrado dto) {
        Gasto gasto=new Gasto();
        gasto.setIdGasto(dto.getIdGasto());
        gasto.setMonto(dto.getMonto());
        gasto.setFecha(new java.sql.Date(StringtoDate(dto.getFecha()).getTime()));
        gasto.setDescripcion(dto.getDescripcion());
        PresupuestoDetalle detalle=new PresupuestoDetalle();
        detalle.setIdPresupuestoDetalle(dto.getIdDetalle());
        gasto.setDetalle(detalle);
        return gasto;
    }

    @Override
    public GastoRegistrado EntitytoDTO(Gasto entity) {
        GastoRegistrado gasto=new GastoRegistrado();
        gasto.setDescripcion(entity.getDescripcion());
        gasto.setFecha(DatetoSring(entity.getFecha()));
        gasto.setIdDetalle(entity.getDetalle().getIdPresupuestoDetalle());
        gasto.setIdGasto(entity.getIdGasto());
        gasto.setMonto(entity.getMonto());
        return gasto;
    }

    @Override
    public List<Gasto> DTOListtoEntityList(List<GastoRegistrado> dtoList) {
        return dtoList.stream().map(this::DTOtoEntity).collect(Collectors.toList());
    }

    @Override
    public List<GastoRegistrado> EntityListtoDTOList(List<Gasto> entityList) {
        return entityList.stream().map(this::EntitytoDTO).collect(Collectors.toList());
    }
    
}
