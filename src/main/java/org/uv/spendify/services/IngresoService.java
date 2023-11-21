/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.uv.spendify.dtos.ingresos.IngresoNuevo;
import org.uv.spendify.dtos.ingresos.IngresoRegistrado;
import org.uv.spendify.converters.ingresos.NuevoIngresoConverter;
import org.uv.spendify.converters.ingresos.IngresoRegistradoConverter;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.models.Ingreso;
import org.uv.spendify.models.Usuario;
import org.uv.spendify.repository.IngresoRepository;
import static org.uv.spendify.validations.Validation.montoValidation;
import static org.uv.spendify.validations.Validation.dateValidation;

/**
 *
 * @author juan
 */
@Service
public class IngresoService {
    private final NuevoIngresoConverter newIncomeConverter;
    private final IngresoRegistradoConverter registeredIncomeConverter;
    private final IngresoRepository incomeRepository;
    private final TipoIngresoService incomeTypeService;
    private final UsuarioService userService;
    
    public IngresoService(
        NuevoIngresoConverter newIncomeConverter,
            IngresoRegistradoConverter registeredIncomeConverter,
            IngresoRepository incomeRepository,
            TipoIngresoService incomeTypeService,
            UsuarioService userService){
        
        this.incomeRepository=incomeRepository;
        this.incomeTypeService=incomeTypeService;
        this.newIncomeConverter=newIncomeConverter;
        this.registeredIncomeConverter=registeredIncomeConverter;
        this.userService=userService;
    }
    
    public IngresoRegistrado saveIncome(IngresoNuevo newIncome){
        if(incomeTypeService.findById(newIncome.getIdTipo())!=null){
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            if(userService.userbyEmail(email)!=null){
                newIncome.setId_usuario(userService.userbyEmail(email).getIdUsuario());
                if(montoValidation(newIncome.getMonto())){
                    String fecha=dateValidation(newIncome.getFecha());
                    if(fecha!=null && fecha.equals("Invalid date")==false){
                        newIncome.setFecha(fecha);
                        Ingreso ingreso=newIncomeConverter.dtotoEntity(newIncome);
                        ingreso=incomeRepository.save(ingreso);
                        return registeredIncomeConverter.entitytoDTO(ingreso);                
                    }else{
                        throw new Exceptions("Invalid date.", HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions("Out of economic range.", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("User not found.", HttpStatus.NOT_FOUND);
            }
        }else{
            throw new Exceptions("Income type not found.", HttpStatus.NOT_FOUND);
        }
    }
    
    public IngresoRegistrado updateIncome(IngresoRegistrado updateIncome, long id){
        IngresoRegistrado ir=null;
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario=userService.userbyEmail(email);
        List<Ingreso> ingresos=usuario.getIngresos();
        if(ingresos!=null){
            Optional<Ingreso> ingreso=incomeRepository.findById(id);
            if(!ingreso.isEmpty()){
                Ingreso i=ingreso.get();
                if(ingresos.contains(i)){
                    if(incomeTypeService.findById(updateIncome.getIdTipo())!=null){
                        if(montoValidation(updateIncome.getMonto())){
                            String fecha=dateValidation(updateIncome.getFecha());
                            if(fecha!=null && fecha.equals("Invalid date")==false){
                                updateIncome.setFecha(fecha);
                                Ingreso u=registeredIncomeConverter.dtotoEntity(updateIncome);
                                i.setDescripcion(u.getDescripcion());
                                i.setFecha(u.getFecha());
                                i.setMonto(u.getMonto());
                                i.setTipo(u.getTipo());
                                i=incomeRepository.save(i);
                                ir=registeredIncomeConverter.entitytoDTO(i);
                            }else{
                                throw new Exceptions("Invalid date.", HttpStatus.CONFLICT);
                            }
                        }else{
                            throw new Exceptions("Out of economic range.", HttpStatus.CONFLICT);
                        }
                    }else{
                        throw new Exceptions("Income type not found.", HttpStatus.NOT_FOUND);
                    }
                }
            }
        }
        return ir;
    }
    
    public boolean deleteIncome(long id){
        boolean bandera=false;
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario=userService.userbyEmail(email);
        List<Ingreso> ingresos=usuario.getIngresos();
        if(ingresos!=null){
            Optional<Ingreso> ingreso=incomeRepository.findById(id);
            if(!ingreso.isEmpty()){
                Ingreso i=ingreso.get();
                if(ingresos.contains(i)){
                    incomeRepository.delete(i);
                    bandera=true;
                }
            }
        }
        return bandera;
    }
    
    //@Transactional
    public List<IngresoRegistrado> getAllIncomesByUser(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u=userService.userbyEmail(email);
        if(u!=null){
            List<Ingreso> ingresos=u.getIngresos();
            return registeredIncomeConverter.entityListtoDTOList(ingresos);
        }else{
            throw new Exceptions("User not found.", HttpStatus.NOT_FOUND);
        }
    }
    
    public boolean deleteAllIncomesByUser(){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario u=userService.userbyEmail(email);
        if(u!=null){
            for(Ingreso i:u.getIngresos()){
                incomeRepository.delete(i);
            }
            return true;
        }else{
            return false;
        }
    }
}
