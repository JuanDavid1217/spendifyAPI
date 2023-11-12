/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.uv.spendify.converters.presupuesto.PresupuestoRegistradoConverter;
import org.uv.spendify.dtos.presupuestos_det.DetalleNuevo;
import org.uv.spendify.dtos.presupuestos_det.DetalleRegistrado;
import org.uv.spendify.converters.presupuesto_det.DetalleRegistradoConverter;
import org.uv.spendify.converters.presupuesto_det.NuevoDetalleConverter;
import org.uv.spendify.dtos.presupuestos.PresupuestoRegistrado;
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
    private final TipoGastoService tipoService;
    private final PresupuestoRepository pr;
    
    public PresupuestoDetalleService(NuevoDetalleConverter nuevoDetalleConverter,
            DetalleRegistradoConverter detalleRegistradoConverter,
            PresupuestoDetalleRepository detalleRepository,
            TipoGastoService tipoService,
            PresupuestoRepository pr){
        this.nuevoDetalleConverter=nuevoDetalleConverter;
        this.detalleRegistradoConverter=detalleRegistradoConverter;
        this.detalleRepository=detalleRepository;
        this.tipoService=tipoService;
        this.pr=pr;
    }
    
    private void tipoDetalle(DetalleNuevo detalle){
        if(tipoService.findById(detalle.getIdTipo())==null){
            throw new Exceptions("Expenses Type not found", HttpStatus.NOT_FOUND);
        }
    }
    
    public DetalleRegistrado addDetailByBudget(DetalleNuevo nuevo, long id){
        DetalleRegistrado detalleRegistrado=null;
        Optional<Presupuesto>presupuestoRegistrado=pr.findById(id);
        if(!presupuestoRegistrado.isEmpty()){
            Presupuesto p=presupuestoRegistrado.get();
            if(p.getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
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
                        PresupuestoDetalle pd=nuevoDetalleConverter.dtotoEntity(nuevo);
                        pd.setPresupuesto(p);
                        pd=detalleRepository.save(pd);
                        detalleRegistrado=detalleRegistradoConverter.entitytoDTO(pd);
                    }else{
                        throw new Exceptions("Yours Detalle economics are more than your presupuesto.", HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions("Out of economic range.", HttpStatus.CONFLICT);
                }
            }
        }
        return detalleRegistrado;
    }
    
    public boolean deleteDetail(long id){
        boolean pase=false;
        Optional<PresupuestoDetalle> presupuestoDetalle=detalleRepository.findById(id);
        if(!presupuestoDetalle.isEmpty()){
            PresupuestoDetalle detalle=presupuestoDetalle.get();
            if(detalle.getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                detalleRepository.delete(detalle);
                pase=true;
            }
        }
        return pase;
    }
    
    public DetalleRegistrado getDetail(long id){
        DetalleRegistrado detalleRegistrado=null;
        Optional<PresupuestoDetalle>presupuestoDetalle=detalleRepository.findById(id);
        if(!presupuestoDetalle.isEmpty()){
            PresupuestoDetalle pd=presupuestoDetalle.get();
            if(pd.getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                detalleRegistrado=detalleRegistradoConverter.entitytoDTO(pd);
            }
        }
        return detalleRegistrado;
    }
    
    public List<DetalleRegistrado> getAllDetailsByBudget(long id){
        List<DetalleRegistrado> lista=null;
        Optional<Presupuesto> presupuestoOptional=pr.findById(id);
        if(!presupuestoOptional.isEmpty()){
            Presupuesto presupuesto=presupuestoOptional.get();
            if(presupuesto.getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
                lista=detalleRegistradoConverter.entityListtoDTOList(presupuesto.getDetalles());
            }
        }
        return lista;
    }
    
    public boolean updateDetail(DetalleNuevo nuevo, long id){
        boolean pase=false;
        Optional<PresupuestoDetalle> detalleOptional=detalleRepository.findById(id);
        if(!detalleOptional.isEmpty()){
            PresupuestoDetalle detalle=detalleOptional.get();
            if(detalle.getPresupuesto().getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
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
                        PresupuestoDetalle pd=nuevoDetalleConverter.dtotoEntity(nuevo);
                        pd.setIdPresupuestoDetalle(detalle.getIdPresupuestoDetalle());
                        pd.setPresupuesto(presupuesto);
                        detalleRepository.save(pd);
                        pase=true;
                    }else{
                        throw new Exceptions("Yours Detalle economics are more than your presupuesto.", HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions("Out of economic range.", HttpStatus.CONFLICT);
                }
            }
        }
        return pase;
    }
}
