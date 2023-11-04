/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.presupuesto_det;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.DTOs.presupuestos_det.DetalleRegistrado;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.Presupuesto;
import org.uv.spendify.models.PresupuestoDetalle;
import org.uv.spendify.models.TipoGasto;

/**
 *
 * @author juan
 */
@Component
public class DetalleRegistradoConverter implements Converter<PresupuestoDetalle, DetalleRegistrado>{

    @Override
    public PresupuestoDetalle DTOtoEntity(DetalleRegistrado dto) {
        PresupuestoDetalle nuevo=new PresupuestoDetalle();
        nuevo.setIdPresupuestoDetalle(dto.getIdPresupuestoDetalle());
        nuevo.setMonto(dto.getMonto());
        Presupuesto p=new Presupuesto();
        p.setIdPresupuesto(dto.getIdPresupuesto());
        nuevo.setPresupuesto(p);
        TipoGasto tipo=new TipoGasto();
        tipo.setIdTipoGasto(dto.getIdTipo());
        nuevo.setTipo(tipo);
        return nuevo;
    }

    @Override
    public DetalleRegistrado EntitytoDTO(PresupuestoDetalle entity) {
        DetalleRegistrado nuevo=new DetalleRegistrado();
        nuevo.setIdPresupuesto(entity.getPresupuesto().getIdPresupuesto());
        nuevo.setIdPresupuestoDetalle(entity.getIdPresupuestoDetalle());
        nuevo.setIdTipo(entity.getTipo().getIdTipoGasto());
        nuevo.setMonto(entity.getMonto());
        return nuevo;
    }

    @Override
    public List<PresupuestoDetalle> DTOListtoEntityList(List<DetalleRegistrado> dtoList) {
        return dtoList.stream().map(this::DTOtoEntity).collect(Collectors.toList());
    }

    @Override
    public List<DetalleRegistrado> EntityListtoDTOList(List<PresupuestoDetalle> entityList) {
        return entityList.stream().map(this::EntitytoDTO).collect(Collectors.toList());
    }
    
}
