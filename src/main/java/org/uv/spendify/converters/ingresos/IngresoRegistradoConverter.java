/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.ingresos;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.dtos.ingresos.IngresoRegistrado;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.Ingreso;
import org.uv.spendify.services.TipoIngresoService;
import org.uv.spendify.services.UsuarioService;
import static org.uv.spendify.validaciones.Validacion.datetoSring;
import static org.uv.spendify.validaciones.Validacion.stringtoDate;

/**
 *
 * @author juan
 */
@Component
public class IngresoRegistradoConverter implements Converter<Ingreso, IngresoRegistrado>{
    
    private final TipoIngresoService tipoIngresoService;
    private final UsuarioService usuarioService;
    
    public IngresoRegistradoConverter(TipoIngresoService tipoIngresoService, UsuarioService usuarioService){
        this.tipoIngresoService=tipoIngresoService;
        this.usuarioService=usuarioService;
    }
    
    @Override
    public Ingreso dtotoEntity(IngresoRegistrado dto) {
        Ingreso ingreso=new Ingreso();
        ingreso.setDescripcion(dto.getDescripcion());
        ingreso.setFecha(new java.sql.Date(stringtoDate(dto.getFecha()).getTime()));
        ingreso.setIdIngreso(dto.getId_ingreso());
        ingreso.setMonto(dto.getMonto());
        ingreso.setTipo(tipoIngresoService.findById(dto.getIdTipo()));
        ingreso.setUsuario(usuarioService.userbyId(dto.getId_usuario()));
        return ingreso;
    }

    @Override
    public IngresoRegistrado entitytoDTO(Ingreso entity) {
        IngresoRegistrado ingreso=new IngresoRegistrado();
        ingreso.setDescripcion(entity.getDescripcion());
        ingreso.setFecha(datetoSring(entity.getFecha()));
        ingreso.setId_ingreso(entity.getIdIngreso());
        String monto=String.valueOf(entity.getMonto()).replace(",", "");
        ingreso.setMonto(new BigDecimal(monto));
        ingreso.setIdTipo(entity.getTipo().getIdTipoIngreso());
        ingreso.setId_usuario(entity.getUsuario().getIdUsuario());
        return ingreso;
    }

    @Override
    public List<Ingreso> dtoListtoEntityList(List<IngresoRegistrado> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }

    @Override
    public List<IngresoRegistrado> entityListtoDTOList(List<Ingreso> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
