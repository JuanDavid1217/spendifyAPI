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
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.spendify.DTOs.presupuestos.PresupuestoNuevo;
import org.uv.spendify.DTOs.presupuestos.PresupuestoBase;
import org.uv.spendify.DTOs.presupuestos.PresupuestoRegistrado;
import org.uv.spendify.DTOs.presupuestos_det.DetalleNuevo;
import org.uv.spendify.converters.presupuesto.NuevoPresupuestoConverter;
import org.uv.spendify.converters.presupuesto.PresupuestoRegistradoConverter;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.models.Presupuesto;
import org.uv.spendify.models.PresupuestoDetalle;
import org.uv.spendify.repository.PresupuestoRepository;
import static org.uv.spendify.validaciones.Validacion.*;

/**
 *
 * @author juan
 */
@Service
public class PresupuestoService {
    private final PresupuestoRepository presupuestoRepository;
    private final NuevoPresupuestoConverter nuevoPresupuestoConverter;
    private final PresupuestoRegistradoConverter presupuestoResgistradoConverter;
    private final UsuarioService userService;
    private final TipoGastoService tipoService;
    
    public PresupuestoService(PresupuestoRepository presupuestoRepository,
            NuevoPresupuestoConverter nuevoPresupuestoConverter,
            PresupuestoRegistradoConverter presupuestoRegistradoConverter,
            UsuarioService userService, TipoGastoService tipoService){
        this.presupuestoRepository=presupuestoRepository;
        this.nuevoPresupuestoConverter=nuevoPresupuestoConverter;
        this.presupuestoResgistradoConverter=presupuestoRegistradoConverter;
        this.userService=userService;
        this.tipoService=tipoService;
    }
    
    private void fechas(String fech1, String fech2){
        String fechaInicio=dateValidation(fech1);
        String fechaFin=dateValidation(fech2);
        if((fechaInicio!=null && fechaInicio.equals("Invalid date")==false)
            && (fechaFin!=null && fechaFin.equals("Invalid date")==false)){
            Date f1=StringtoDate(fechaInicio);
            Date f2=StringtoDate(fechaFin);
            if(DAYS.between(f1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
                    f2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())!=30){
                throw new Exceptions("Invalid date range.", HttpStatus.CONFLICT);
            }
        }else{
            throw new Exceptions("Invalid date.", HttpStatus.CONFLICT);
        }
    }
    
    private void monto(List<DetalleNuevo> detalles, BigDecimal total){
        BigDecimal suma=new BigDecimal(0);
        for(DetalleNuevo detalle:detalles){
            if(montoValidation(detalle.getMonto())){
                suma=suma.add(detalle.getMonto());
            }else{
                throw new Exceptions("Out of Detalle economic range.", HttpStatus.CONFLICT);
            }
        }
        if(suma.compareTo(total)>0){
            throw new Exceptions("Yours Detalle economics are more than your presupuesto.", HttpStatus.CONFLICT);
        }
    }
    
    private void tipoDetalle(List<DetalleNuevo> detalles){
        for(DetalleNuevo detalle:detalles){
            if(tipoService.findById(detalle.getIdTipo())==null){
                throw new Exceptions("Expenses Type not found", HttpStatus.NOT_FOUND);
            }
        }
    }
    
    @Transactional
    public PresupuestoRegistrado savePresupuesto(PresupuestoNuevo nuevo){
        if(userService.userbyId(nuevo.getIdUsuario())!=null){
            if(montoValidation(nuevo.getMontoTotal())){
                fechas(nuevo.getFechaInicio(), nuevo.getFechaFin());
                nuevo.setFechaInicio(dateValidation(nuevo.getFechaInicio()));
                nuevo.setFechaFin(dateValidation(nuevo.getFechaFin()));
                monto(nuevo.getDetalles(), nuevo.getMontoTotal());
                tipoDetalle(nuevo.getDetalles());
                Presupuesto p=nuevoPresupuestoConverter.DTOtoEntity(nuevo);
                p=presupuestoRepository.save(p);
                List<PresupuestoDetalle> lista=p.getDetalles();
                for(int i=0; i<lista.size(); i++){
                    for(int j=0; j<lista.size(); j++){
                        if(lista.get(i).getTipo().getIdTipoGasto()==lista.get(j).getTipo().getIdTipoGasto()&&i!=j){
                            throw new Exceptions("Can´t have the same type of gasto in one budget.", HttpStatus.CONFLICT);
                        }
                    }
                    PresupuestoDetalle pd=lista.get(i);
                    pd.setPresupuesto(p);
                    lista.set(i,pd);
                }
                p=presupuestoRepository.save(p);
                return presupuestoResgistradoConverter.EntitytoDTO(p);
            }else{
                throw new Exceptions("Out of economic range.", HttpStatus.CONFLICT);
            }
        }else{
            throw new Exceptions("User not found.", HttpStatus.NOT_FOUND);
        }
    }
    
    public boolean updatePresupuesto(PresupuestoBase nuevo, long id){
        Optional<Presupuesto> pre=presupuestoRepository.findById(id);
        if(!pre.isEmpty()){
            Presupuesto p=pre.get();
            if(montoValidation(nuevo.getMontoTotal())){
                fechas(nuevo.getFechaInicio(), nuevo.getFechaFin());
                nuevo.setFechaInicio(dateValidation(nuevo.getFechaInicio()));
                nuevo.setFechaFin(dateValidation(nuevo.getFechaFin()));
                BigDecimal suma=new BigDecimal(0);
                for(PresupuestoDetalle pd:p.getDetalles()){
                    suma=suma.add(pd.getMonto());
                }
                if(suma.compareTo(nuevo.getMontoTotal())<=0){
                    p.setFechaFin(new java.sql.Date(StringtoDate(nuevo.getFechaFin()).getTime()));
                    p.setFechaInicio(new java.sql.Date(StringtoDate(nuevo.getFechaInicio()).getTime()));
                    p.setMontoTotal(nuevo.getMontoTotal());
                    presupuestoRepository.save(p);
                    return true;
                }else{
                    throw new Exceptions("Yours Detalle economics are more than your presupuesto.", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("Out of economic range.", HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public boolean deleteAllPresupuestoByUser(long id){
        if(userService.userbyId(id)!=null){
            for(Presupuesto p:userService.userbyId(id).getPresupuestos()){
                presupuestoRepository.delete(p);
            }
            return true;
        }else{
            return false;
        }
    }
    
    public boolean deletePresupuesto(long id){
        Optional<Presupuesto>p=presupuestoRepository.findById(id);
        if(!p.isEmpty()){
            presupuestoRepository.delete(p.get());
            return true;
        }else{
            return false;
        }
    }
    
    public List<PresupuestoRegistrado> getAllBudgetByUser(long id){
        if(userService.userbyId(id)!=null){
            return presupuestoResgistradoConverter.EntityListtoDTOList(userService.userbyId(id).getPresupuestos());
        }else{
            return null;
        }
    }
    
    public PresupuestoRegistrado getBudgetById(long id){
        Optional<Presupuesto> p=presupuestoRepository.findById(id);
        if(!p.isEmpty()){
            return presupuestoResgistradoConverter.EntitytoDTO(p.get());
        }else{
            return null;
        }
    }
}
