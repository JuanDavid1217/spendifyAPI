/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.presupuesto_det;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.dtos.presupuestos_det.DetalleNuevo;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.PresupuestoDetalle;
import org.uv.spendify.models.TipoGasto;

/**
 *
 * @author juan
 */
@Component
public class NuevoDetalleConverter implements Converter<PresupuestoDetalle, DetalleNuevo>{

    @Override
    public PresupuestoDetalle dtotoEntity(DetalleNuevo dto) {
        PresupuestoDetalle nuevo=new PresupuestoDetalle();
        nuevo.setMonto(dto.getMonto());
        TipoGasto tipo=new TipoGasto();
        tipo.setIdTipoGasto(dto.getIdTipo());
        nuevo.setTipo(tipo);
        return nuevo;
    }

    @Override
    public DetalleNuevo entitytoDTO(PresupuestoDetalle entity) {
        DetalleNuevo nuevo=new DetalleNuevo();
        nuevo.setIdTipo(entity.getTipo().getIdTipoGasto());
        nuevo.setMonto(entity.getMonto());
        return nuevo;
    }

    @Override
    public List<PresupuestoDetalle> dtoListtoEntityList(List<DetalleNuevo> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }

    @Override
    public List<DetalleNuevo> entityListtoDTOList(List<PresupuestoDetalle> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
