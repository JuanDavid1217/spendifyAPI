/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.spendify.converters;

import java.util.List;

/**
 *
 * @author juan
 */
public interface Converter<Entity, DTO> {
    public Entity DTOtoEntity(DTO dto);
    public DTO EntitytoDTO(Entity entity);
    
    public List<Entity> DTOListtoEntityList(List<DTO> dtoList);
    public List<DTO> EntityListtoDTOList(List<Entity> entityList);
}
