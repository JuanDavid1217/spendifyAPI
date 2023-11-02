/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.spendify.DTOs.ingresos.IngresoNuevo;
import org.uv.spendify.DTOs.ingresos.IngresoRegistrado;
import org.uv.spendify.converters.incomes.NuevoIngresoConverter;
import org.uv.spendify.converters.incomes.IngresoRegistradoConverter;
import org.uv.spendify.exceptions.Exceptions;
import org.uv.spendify.models.Ingreso;
import org.uv.spendify.models.Usuario;
import org.uv.spendify.repository.IngresoRepository;
import static org.uv.spendify.validaciones.Validacion.montoValidation;
import static org.uv.spendify.validaciones.Validacion.dateValidation;

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
        if(incomeTypeService.findById(newIncome.getId_tipo())!=null){
            if(userService.userbyId(newIncome.getId_usuario())!=null){
                if(montoValidation(newIncome.getMonto())){
                    String fecha=dateValidation(newIncome.getFecha());
                    if(fecha!=null && fecha.equals("Invalid date")==false){
                        Ingreso ingreso=new Ingreso();
                        newIncome.setFecha(fecha);
                        ingreso=newIncomeConverter.DTOtoEntity(newIncome);
                        ingreso=incomeRepository.save(ingreso);
                        IngresoRegistrado registeredIncome=registeredIncomeConverter.EntitytoDTO(ingreso);
                        return registeredIncome;
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
        Optional<Ingreso> ingreso=incomeRepository.findById(id);
        if(!ingreso.isEmpty()){
            if(incomeTypeService.findById(updateIncome.getId_tipo())!=null){
                if(montoValidation(updateIncome.getMonto())){
                    String fecha=dateValidation(updateIncome.getFecha());
                    if(fecha!=null && fecha.equals("Invalid date")==false){
                        Ingreso i=ingreso.get();
                        updateIncome.setFecha(fecha);
                        Ingreso u=registeredIncomeConverter.DTOtoEntity(updateIncome);
                        i.setDescripcion(u.getDescripcion());
                        i.setFecha(u.getFecha());
                        i.setMonto(u.getMonto());
                        i.setTipo(u.getTipo());
                        i=incomeRepository.save(i);
                        return registeredIncomeConverter.EntitytoDTO(i);
                    }else{
                        throw new Exceptions("Invalid date.", HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions("Out of economic range.", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("Income type not found.", HttpStatus.NOT_FOUND);
            }
        }else{
            return null;
        }
    }
    
    public boolean deleteIncome(long id){
        Optional<Ingreso> ingreso=incomeRepository.findById(id);
        if(!ingreso.isEmpty()){
            incomeRepository.delete(ingreso.get());
            return true;
        }else{
            return false;
        }
    }
    
    //@Transactional
    public List<IngresoRegistrado> getAllIncomesByUser(long id){
        Usuario u=userService.userbyId(id);
        if(u!=null){
            List<Ingreso> ingresos=u.getIngresos();
            return registeredIncomeConverter.EntityListtoDTOList(ingresos);
        }else{
            throw new Exceptions("User not found.", HttpStatus.NOT_FOUND);
        }
    }
    
    public boolean deleteAllIncomesByUser(long id){
        Usuario u=userService.userbyId(id);
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
