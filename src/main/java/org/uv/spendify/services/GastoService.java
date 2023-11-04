/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.math.BigDecimal;
import java.time.ZoneId;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.spendify.DTOs.gastos.GastoNuevo;
import org.uv.spendify.DTOs.gastos.GastoRegistrado;
import org.uv.spendify.converters.gastos.GastoNuevoConverter;
import org.uv.spendify.converters.gastos.GastoRegistradoConverter;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.models.Gasto;
import org.uv.spendify.models.PresupuestoDetalle;
import org.uv.spendify.repository.GastoRepository;
import org.uv.spendify.repository.PresupuestoDetalleRepository;
import static org.uv.spendify.validaciones.Validacion.StringtoDate;
import static org.uv.spendify.validaciones.Validacion.DatetoSring;
import static org.uv.spendify.validaciones.Validacion.dateValidation;

/**
 *
 * @author juan
 */
@Service
public class GastoService {
    private final GastoNuevoConverter gastoNuevoConverter;
    private final GastoRegistradoConverter gastoRegistradoConverter;
    private final GastoRepository gastoRepository;
    private final PresupuestoDetalleRepository presupuestoDetalleRepository;
    
    public GastoService(GastoNuevoConverter gastoNuevoConverter,
            GastoRegistradoConverter gastoRegistradoConverter,
            GastoRepository gastoRepository,
            PresupuestoDetalleRepository presupuestoDetalleRepository){
        this.gastoNuevoConverter=gastoNuevoConverter;
        this.gastoRegistradoConverter=gastoRegistradoConverter;
        this.gastoRepository=gastoRepository;
        this.presupuestoDetalleRepository=presupuestoDetalleRepository;
    }
    
    private void fechas(String fech1, String fech2){
        String fechaInicio=dateValidation(fech1);
        String fechaFin=dateValidation(fech2);
            Date f1=StringtoDate(fechaInicio);
            Date f2=StringtoDate(fechaFin);
            long lapso=DAYS.between(f1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                    f2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            if(lapso<0 || lapso>30){
                throw new Exceptions("Invalid date range.", HttpStatus.CONFLICT);
            }
    }
    
    public GastoRegistrado saveGasto(GastoNuevo nuevo){
        Optional<PresupuestoDetalle> pd=presupuestoDetalleRepository.findById(nuevo.getIdDetalle());
        if(!pd.isEmpty()){
            String fecha=dateValidation(nuevo.getFecha());
            if(fecha!=null && fecha.equals("Invalid date")==false){
                String f=DatetoSring(pd.get().getPresupuesto().getFechaInicio());
                fechas(f, nuevo.getFecha());
                nuevo.setFecha(fecha);
                List<Gasto> lista=pd.get().getGastos();
                BigDecimal suma=new BigDecimal(0);
                for(Gasto g:lista){
                    suma=suma.add(g.getMonto());
                }
                if((pd.get().getMonto().subtract(suma)).compareTo(nuevo.getMonto())>=0){
                    Gasto gasto=gastoNuevoConverter.DTOtoEntity(nuevo);
                    gasto=gastoRepository.save(gasto);
                    return gastoRegistradoConverter.EntitytoDTO(gasto);
                }else{
                    throw new Exceptions("You are out of detail limit.", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("Invalid date.", HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean updateGasto(GastoNuevo nuevo, long id){
        Optional<Gasto> g=gastoRepository.findById(id);
        if(!g.isEmpty()){
            Gasto gas=g.get();
            String fecha=dateValidation(nuevo.getFecha());
            PresupuestoDetalle pd=gas.getDetalle();
            if(fecha!=null && fecha.equals("Invalid date")==false){
                String f=DatetoSring(pd.getPresupuesto().getFechaInicio());
                fechas(f, nuevo.getFecha());
                nuevo.setFecha(fecha);
                List<Gasto> lista=pd.getGastos();
                BigDecimal suma=new BigDecimal(0);
                for(Gasto gasto:lista){
                    if(gasto.getIdGasto()!=id){
                        suma=suma.add(gasto.getMonto());
                    }
                }
                if((pd.getMonto().subtract(suma)).compareTo(nuevo.getMonto())>=0){
                    Gasto gasto=gastoNuevoConverter.DTOtoEntity(nuevo);
                    gas.setDescripcion(gasto.getDescripcion());
                    gas.setFecha(gasto.getFecha());
                    gas.setMonto(gasto.getMonto());
                    gastoRepository.save(gas);
                    return true;
                }else{
                    throw new Exceptions("You are out of detail limit.", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("Invalid date.", HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }

    public GastoRegistrado getGasto(long id){
        Optional<Gasto> g=gastoRepository.findById(id);
        if(!g.isEmpty()){
            return gastoRegistradoConverter.EntitytoDTO(g.get());
        }else{
            return null;
        }
    }
    
    public List<GastoRegistrado> getAllGastoByDetail(long id){
        Optional<PresupuestoDetalle> pd=presupuestoDetalleRepository.findById(id);
        if(!pd.isEmpty()){
            return gastoRegistradoConverter.EntityListtoDTOList(pd.get().getGastos());
        }else{
            return null;
        }
    }
    
    public boolean deleteGasto(long id){
        Optional<Gasto> g=gastoRepository.findById(id);
        if(!g.isEmpty()){
            gastoRepository.delete(g.get());
            return true;
        }else{
            return false;
        }
    }
    
    public boolean deleteAllGastoByDetail(long id){
        Optional<PresupuestoDetalle> pd=presupuestoDetalleRepository.findById(id);
        if(!pd.isEmpty()){
            for(Gasto gasto:pd.get().getGastos()){
                gastoRepository.delete(gasto);
            }
            return true; 
        }else{
            return false;
        }
    }
}
