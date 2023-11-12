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
public interface Converter<T, S> {
    public T dtotoEntity(S dto);
    public S entitytoDTO(T entity);
    
    public List<T> dtoListtoEntityList(List<S> dtoList);
    public List<S> entityListtoDTOList(List<T> entityList);
}
