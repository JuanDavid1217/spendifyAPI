/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.math.BigDecimal;
import java.time.ZoneId;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.uv.spendify.dtos.gastos.GastoNuevo;
import org.uv.spendify.dtos.gastos.GastoRegistrado;
import org.uv.spendify.converters.gastos.GastoNuevoConverter;
import org.uv.spendify.converters.gastos.GastoRegistradoConverter;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.models.Gasto;
import org.uv.spendify.models.Presupuesto;
import org.uv.spendify.models.PresupuestoDetalle;
import org.uv.spendify.models.Usuario;
import org.uv.spendify.repository.GastoRepository;
import org.uv.spendify.repository.PresupuestoDetalleRepository;
import static org.uv.spendify.validations.Validation.stringtoDate;
import static org.uv.spendify.validations.Validation.datetoSring;
import static org.uv.spendify.validations.Validation.dateValidation;

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
    private final UsuarioService userService;
    
    public GastoService(GastoNuevoConverter gastoNuevoConverter,
            GastoRegistradoConverter gastoRegistradoConverter,
            GastoRepository gastoRepository,
            PresupuestoDetalleRepository presupuestoDetalleRepository,
            UsuarioService userService){
        this.gastoNuevoConverter=gastoNuevoConverter;
        this.gastoRegistradoConverter=gastoRegistradoConverter;
        this.gastoRepository=gastoRepository;
        this.presupuestoDetalleRepository=presupuestoDetalleRepository;
        this.userService=userService;
    }
    
    private void fechas(String fech1, String fech2){
        String fechaInicio=dateValidation(fech1);
        String fechaFin=dateValidation(fech2);
            Date f1=stringtoDate(fechaInicio);
            Date f2=stringtoDate(fechaFin);
            long lapso=DAYS.between(f1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                    f2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            if(lapso<0 || lapso>30){
                throw new Exceptions("Invalid date range.", HttpStatus.CONFLICT);
            }
    }
    
    public GastoRegistrado saveGasto(GastoNuevo nuevo){
        GastoRegistrado gastoRegistrado=null;
        Optional<PresupuestoDetalle> pd=presupuestoDetalleRepository.findById(nuevo.getIdDetalle());
        if(!pd.isEmpty()){
            PresupuestoDetalle presupuestoDetalle=pd.get();
            if(presupuestoDetalle.getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                String fecha=dateValidation(nuevo.getFecha());
                if(fecha!=null && fecha.equals("Invalid date")==false){
                    String f=datetoSring(presupuestoDetalle.getPresupuesto().getFechaInicio());
                    fechas(f, nuevo.getFecha());
                    nuevo.setFecha(fecha);
                    List<Gasto> lista=presupuestoDetalle.getGastos();
                    BigDecimal suma=new BigDecimal(0);
                    for(Gasto g:lista){
                        suma=suma.add(g.getMonto());
                    }
                    if((presupuestoDetalle.getMonto().subtract(suma)).compareTo(nuevo.getMonto())>=0){
                        Gasto gasto=gastoNuevoConverter.dtotoEntity(nuevo);
                        gasto=gastoRepository.save(gasto);
                        gastoRegistrado=gastoRegistradoConverter.entitytoDTO(gasto);
                    }else{
                        throw new Exceptions("You are out of detail limit.", HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions("Invalid date.", HttpStatus.CONFLICT);
                }
            }
        }
        return gastoRegistrado;
    }
    
    public boolean updateGasto(GastoNuevo nuevo, long id){
        boolean pase=false;
        Optional<Gasto> g=gastoRepository.findById(id);
        if(!g.isEmpty()){
            Gasto gas=g.get();
            if(gas.getDetalle().getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                String fecha=dateValidation(nuevo.getFecha());
                PresupuestoDetalle pd=gas.getDetalle();
                if(fecha!=null && fecha.equals("Invalid date")==false){
                    String f=datetoSring(pd.getPresupuesto().getFechaInicio());
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
                        Gasto gasto=gastoNuevoConverter.dtotoEntity(nuevo);
                        gas.setDescripcion(gasto.getDescripcion());
                        gas.setFecha(gasto.getFecha());
                        gas.setMonto(gasto.getMonto());
                        gastoRepository.save(gas);
                        pase=true;
                    }else{
                        throw new Exceptions("You are out of detail limit.", HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions("Invalid date.", HttpStatus.CONFLICT);
                }
            }
        }
        return pase;
    }

    public GastoRegistrado getGasto(long id){
        GastoRegistrado gastoRegistrado=null;
        Optional<Gasto> g=gastoRepository.findById(id);
        if(!g.isEmpty()){
            Gasto gas=g.get();
            if(gas.getDetalle().getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                gastoRegistrado=gastoRegistradoConverter.entitytoDTO(gas);
            }
        }
        return gastoRegistrado;
    }
    
    public List<GastoRegistrado> getAllGastoByDetail(long id){
        List<GastoRegistrado> gastosRegistrados=null;
        Optional<PresupuestoDetalle> pd=presupuestoDetalleRepository.findById(id);
        if(!pd.isEmpty()){
            PresupuestoDetalle presupuestoDetalle=pd.get();
            if(presupuestoDetalle.getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                gastosRegistrados=gastoRegistradoConverter.entityListtoDTOList(presupuestoDetalle.getGastos());
            }
        }
        return gastosRegistrados;
    }
    
    public BigDecimal sumOfGastosByUser(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u=userService.userbyEmail(email);
        if(u!=null){
            BigDecimal total=new BigDecimal(0);
            List<Presupuesto> presupuestos=u.getPresupuestos();
            for(Presupuesto p:presupuestos){
                List<PresupuestoDetalle> detalles=p.getDetalles();
                for(PresupuestoDetalle pd:detalles){
                    List<Gasto> gastos=pd.getGastos();
                    for(Gasto g:gastos){
                        total=total.add(g.getMonto());
                    }
                }
            }
            return total;
        }else{
            throw new Exceptions("User not found.", HttpStatus.NOT_FOUND);
        }
    }
    
    public List<GastoRegistrado> getAllExpensesByUser(){
        List<GastoRegistrado> gastosRegistrados=new ArrayList<>();
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u=userService.userbyEmail(email);
        if(u!=null){
            List<Presupuesto> presupuestos=u.getPresupuestos();
            for(Presupuesto p:presupuestos){
                List<PresupuestoDetalle> detalles=p.getDetalles();
                for(PresupuestoDetalle pd:detalles){
                    List<Gasto> gastos=pd.getGastos();
                    List<GastoRegistrado> list=gastoRegistradoConverter.entityListtoDTOList(gastos);
                    for(GastoRegistrado gr:list){
                        gastosRegistrados.add(gr);
                    }
                }
            }
            return gastosRegistrados;
        }else{
            throw new Exceptions("User not found.", HttpStatus.NOT_FOUND);
        }
    }
    
    public boolean deleteGasto(long id){
        boolean bandera=false;
        Optional<Gasto> g=gastoRepository.findById(id);
        if(!g.isEmpty()){
            Gasto gas=g.get();
            if(gas.getDetalle().getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                gastoRepository.delete(gas);
                bandera=true;
            }
        }
        return bandera;
    }
    
    public boolean deleteAllGastoByDetail(long id){
        boolean pase=false;
        Optional<PresupuestoDetalle> pd=presupuestoDetalleRepository.findById(id);
        if(!pd.isEmpty()){
            PresupuestoDetalle presupuestoDetalle=pd.get();
            if(presupuestoDetalle.getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                for(Gasto gasto:presupuestoDetalle.getGastos()){
                    gastoRepository.delete(gasto);
                }
                pase=true;
            } 
        }
        return pase;
    }
}
