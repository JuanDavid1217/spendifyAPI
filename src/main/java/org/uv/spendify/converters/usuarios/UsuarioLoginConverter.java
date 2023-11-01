/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.DTOs.usuarios.UsuarioLogin;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.Usuario;

/**
 *
 * @author juan
 */
@Component
public class UsuarioLoginConverter implements Converter<Usuario, UsuarioLogin> {

    @Override
    public Usuario DTOtoEntity(UsuarioLogin dto) {
        Usuario usuario=new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        return usuario;
    }

    @Override
    public UsuarioLogin EntitytoDTO(Usuario entity) {
        UsuarioLogin usuario=new UsuarioLogin();
        usuario.setEmail(entity.getEmail());
        usuario.setPassword(entity.getPassword());
        return usuario;
    }

    @Override
    public List<Usuario> DTOListtoEntityList(List<UsuarioLogin> dtoList) {
        return dtoList.stream().map(this::DTOtoEntity).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioLogin> EntityListtoDTOList(List<Usuario> entityList) {
        return entityList.stream().map(this::EntitytoDTO).collect(Collectors.toList());
    }
    
}
