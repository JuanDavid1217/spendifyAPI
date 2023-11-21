/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.gastos;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.dtos.gastos.GastoRegistrado;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.Gasto;
import org.uv.spendify.models.PresupuestoDetalle;
import static org.uv.spendify.validations.Validation.datetoSring;
import static org.uv.spendify.validations.Validation.stringtoDate;

/**
 *
 * @author juan
 */
@Component
public class GastoRegistradoConverter implements Converter<Gasto, GastoRegistrado>{

    @Override
    public Gasto dtotoEntity(GastoRegistrado dto) {
        Gasto gasto=new Gasto();
        gasto.setIdGasto(dto.getIdGasto());
        gasto.setMonto(dto.getMonto());
        gasto.setFecha(new java.sql.Date(stringtoDate(dto.getFecha()).getTime()));
        gasto.setDescripcion(dto.getDescripcion());
        PresupuestoDetalle detalle=new PresupuestoDetalle();
        detalle.setIdPresupuestoDetalle(dto.getIdDetalle());
        gasto.setDetalle(detalle);
        return gasto;
    }

    @Override
    public GastoRegistrado entitytoDTO(Gasto entity) {
        GastoRegistrado gasto=new GastoRegistrado();
        gasto.setDescripcion(entity.getDescripcion());
        gasto.setFecha(datetoSring(entity.getFecha()));
        gasto.setIdDetalle(entity.getDetalle().getIdPresupuestoDetalle());
        gasto.setIdGasto(entity.getIdGasto());
        gasto.setMonto(entity.getMonto());
        return gasto;
    }

    @Override
    public List<Gasto> dtoListtoEntityList(List<GastoRegistrado> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }

    @Override
    public List<GastoRegistrado> entityListtoDTOList(List<Gasto> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
