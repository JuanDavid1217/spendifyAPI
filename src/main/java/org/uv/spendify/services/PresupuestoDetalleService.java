/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.spendify.DTOs.presupuestos_det.DetalleNuevo;
import org.uv.spendify.DTOs.presupuestos_det.DetalleRegistrado;
import org.uv.spendify.converters.presupuesto_det.DetalleRegistradoConverter;
import org.uv.spendify.converters.presupuesto_det.NuevoDetalleConverter;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.models.Presupuesto;
import org.uv.spendify.models.PresupuestoDetalle;
import org.uv.spendify.repository.PresupuestoDetalleRepository;
import org.uv.spendify.repository.PresupuestoRepository;
import static org.uv.spendify.validaciones.Validacion.montoValidation;

/**
 *
 * @author juan
 */
@Service
public class PresupuestoDetalleService {
    private final NuevoDetalleConverter nuevoDetalleConverter;
    private final DetalleRegistradoConverter detalleRegistradoConverter;
    private final PresupuestoDetalleRepository detalleRepository;
    private final PresupuestoRepository presupuestoRepository;
    private final TipoGastoService tipoService;
    
    public PresupuestoDetalleService(NuevoDetalleConverter nuevoDetalleConverter,
            DetalleRegistradoConverter detalleRegistradoConverter,
            PresupuestoDetalleRepository detalleRepository,
            PresupuestoRepository presupuestoRepository, TipoGastoService tipoService){
        this.nuevoDetalleConverter=nuevoDetalleConverter;
        this.detalleRegistradoConverter=detalleRegistradoConverter;
        this.detalleRepository=detalleRepository;
        this.presupuestoRepository=presupuestoRepository;
        this.tipoService=tipoService;
    }
    
    private void tipoDetalle(DetalleNuevo detalle){
        if(tipoService.findById(detalle.getIdTipo())==null){
            throw new Exceptions("Expenses Type not found", HttpStatus.NOT_FOUND);
        }
    }
    
    public DetalleRegistrado addDetailByBudget(DetalleNuevo nuevo, long id){
        Optional<Presupuesto> presupuesto=presupuestoRepository.findById(id);
        if(!presupuesto.isEmpty()){
            Presupuesto p=presupuesto.get();
            tipoDetalle(nuevo);
            for(PresupuestoDetalle pd:p.getDetalles()){
                if(pd.getTipo().getIdTipoGasto()==nuevo.getIdTipo()){
                    throw new Exceptions("Can´t have the same type of gasto in one budget.", HttpStatus.CONFLICT);
                }
            }
            if(montoValidation(nuevo.getMonto())){
                BigDecimal suma=new BigDecimal(0);
                for(PresupuestoDetalle pd:p.getDetalles()){
                    suma=suma.add(pd.getMonto());
                }
                if((p.getMontoTotal().subtract(suma)).compareTo(nuevo.getMonto())>=0){
                    PresupuestoDetalle pd=nuevoDetalleConverter.DTOtoEntity(nuevo);
                    pd.setPresupuesto(p);
                    pd=detalleRepository.save(pd);
                    return detalleRegistradoConverter.EntitytoDTO(pd);
                }else{
                    throw new Exceptions("Yours Detalle economics are more than your presupuesto.", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("Out of economic range.", HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean deleteDetail(long id){
        Optional<PresupuestoDetalle> pd=detalleRepository.findById(id);
        if(!pd.isEmpty()){
            detalleRepository.delete(pd.get());
            return true;
        }else{
            return false;
        }
    }
    
    public DetalleRegistrado getDetail(long id){
        Optional<PresupuestoDetalle> pd=detalleRepository.findById(id);
        if(!pd.isEmpty()){
            return detalleRegistradoConverter.EntitytoDTO(pd.get());
        }else{
            return null;
        }
    }
    
    public List<DetalleRegistrado> getAllDetailsByBudget(long id){
        Optional<Presupuesto> presupuesto=presupuestoRepository.findById(id);
        if(!presupuesto.isEmpty()){
            return detalleRegistradoConverter.EntityListtoDTOList(presupuesto.get().getDetalles());
        }else{
            return null;
        }
    }
    
    public boolean updateDetail(DetalleNuevo nuevo, long id){
        Optional<PresupuestoDetalle> pdetalle=detalleRepository.findById(id);
        if(!pdetalle.isEmpty()){
            PresupuestoDetalle detalle=pdetalle.get();
            tipoDetalle(nuevo);
            if(montoValidation(nuevo.getMonto())){
                Presupuesto presupuesto=detalle.getPresupuesto();
                for(PresupuestoDetalle pd:presupuesto.getDetalles()){
                    if(pd.getTipo().getIdTipoGasto()==nuevo.getIdTipo()&&pd.getIdPresupuestoDetalle()!=id){
                        throw new Exceptions("Can´t have the same type of gasto in one budget.", HttpStatus.CONFLICT);
                    }
                }
                BigDecimal suma=new BigDecimal(0);
                for(PresupuestoDetalle pd:presupuesto.getDetalles()){
                    if(pd.getIdPresupuestoDetalle()!=id){
                        suma=suma.add(pd.getMonto());
                    }
                }
                if((presupuesto.getMontoTotal().subtract(suma)).compareTo(nuevo.getMonto())>=0){
                    PresupuestoDetalle pd=nuevoDetalleConverter.DTOtoEntity(nuevo);
                    pd.setIdPresupuestoDetalle(detalle.getIdPresupuestoDetalle());
                    pd.setPresupuesto(presupuesto);
                    detalleRepository.save(pd);
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
}
