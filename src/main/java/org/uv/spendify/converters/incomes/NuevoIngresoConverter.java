/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.incomes;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.DTOs.ingresos.IngresoNuevo;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.Ingreso;
import org.uv.spendify.models.TipoIngreso;
import org.uv.spendify.models.Usuario;
import org.uv.spendify.repository.TipoIngresoRepository;
import static org.uv.spendify.validaciones.Validacion.StringtoDate;
import static org.uv.spendify.validaciones.Validacion.DatetoSring;
/**
 *
 * @author juan
 */
@Component
public class NuevoIngresoConverter implements Converter<Ingreso, IngresoNuevo>{
    private final TipoIngresoRepository tipoIngresoRepositorio;
    
    public NuevoIngresoConverter(TipoIngresoRepository tipoIngresoRepositorio){
        this.tipoIngresoRepositorio=tipoIngresoRepositorio;
    }
    
    @Override
    public Ingreso DTOtoEntity(IngresoNuevo dto) {
        Ingreso ingreso=new Ingreso();
        ingreso.setDescripcion(dto.getDescripcion());
        ingreso.setFecha(new java.sql.Date(StringtoDate(dto.getFecha()).getTime()));
        ingreso.setMonto(dto.getMonto());
        TipoIngreso i=new TipoIngreso();
        i.setIdTipoIngreso(dto.getId_tipo());
        ingreso.setTipo(i);
        Usuario u=new Usuario();
        u.setIdUsuario(dto.getId_usuario());
        ingreso.setUsuario(u);
        return ingreso;
    }

    @Override
    public IngresoNuevo EntitytoDTO(Ingreso entity) {
        IngresoNuevo newIncome=new IngresoNuevo();
        newIncome.setDescripcion(entity.getDescripcion());
        newIncome.setFecha(DatetoSring(entity.getFecha()));
        newIncome.setId_tipo(entity.getTipo().getIdTipoIngreso());
        newIncome.setId_usuario(entity.getUsuario().getIdUsuario());
        String monto=String.valueOf(entity.getMonto()).replace(",", "");
        newIncome.setMonto(new BigDecimal(monto));
        return newIncome;
    }

    @Override
    public List<Ingreso> DTOListtoEntityList(List<IngresoNuevo> dtoList) {
        return dtoList.stream().map(this::DTOtoEntity).collect(Collectors.toList());
    }

    @Override
    public List<IngresoNuevo> EntityListtoDTOList(List<Ingreso> entityList) {
        return entityList.stream().map(this::EntitytoDTO).collect(Collectors.toList());
    }

    
}
