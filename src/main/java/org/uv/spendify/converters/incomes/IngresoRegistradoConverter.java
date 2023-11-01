/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.incomes;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.DTOs.ingresos.IngresoRegistrado;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.Ingreso;
import org.uv.spendify.services.TipoIngresoService;
import org.uv.spendify.services.UsuarioService;
import static org.uv.spendify.validaciones.Validacion.DatetoSring;
import static org.uv.spendify.validaciones.Validacion.StringtoDate;

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
    public Ingreso DTOtoEntity(IngresoRegistrado dto) {
        Ingreso ingreso=new Ingreso();
        ingreso.setDescripcion(dto.getDescripcion());
        ingreso.setFecha(new java.sql.Date(StringtoDate(dto.getFecha()).getTime()));
        ingreso.setIdIngreso(dto.getId_ingreso());
        ingreso.setMonto(dto.getMonto());
        ingreso.setTipo(tipoIngresoService.findById(dto.getId_tipo()));
        ingreso.setUsuario(usuarioService.userbyId(dto.getId_usuario()));
        return ingreso;
    }

    @Override
    public IngresoRegistrado EntitytoDTO(Ingreso entity) {
        IngresoRegistrado ingreso=new IngresoRegistrado();
        ingreso.setDescripcion(entity.getDescripcion());
        ingreso.setFecha(DatetoSring(entity.getFecha()));
        ingreso.setId_ingreso(entity.getIdIngreso());
        String monto=String.valueOf(entity.getMonto()).replace(",", "");
        System.out.print(monto);
        ingreso.setMonto(new BigDecimal(monto));
        ingreso.setId_tipo(entity.getTipo().getIdTipoIngreso());
        ingreso.setId_usuario(entity.getUsuario().getIdUsuario());
        return ingreso;
    }

    @Override
    public List<Ingreso> DTOListtoEntityList(List<IngresoRegistrado> dtoList) {
        return dtoList.stream().map(this::DTOtoEntity).collect(Collectors.toList());
    }

    @Override
    public List<IngresoRegistrado> EntityListtoDTOList(List<Ingreso> entityList) {
        return entityList.stream().map(this::EntitytoDTO).collect(Collectors.toList());
    }
    
}
