/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.dtos.usuarios.UsuarioAcces;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.Usuario;

/**
 *
 * @author juan
 */
@Component
public class UsuarioAccessConverter implements Converter<Usuario, UsuarioAcces>{

    @Override
    public Usuario dtotoEntity(UsuarioAcces dto) {
        Usuario usuario=new Usuario();
        usuario.setIdUsuario(dto.getId());
        usuario.setEdad(dto.getEdad());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidoPaterno(dto.getApellidoPaterno());
        usuario.setApellidoMaterno(dto.getApellidoMaterno());
        return usuario;
    }

    @Override
    public UsuarioAcces entitytoDTO(Usuario entity) {
        UsuarioAcces usuario=new UsuarioAcces();
        usuario.setEdad(entity.getEdad());
        usuario.setEmail(entity.getEmail());
        usuario.setId(entity.getIdUsuario());
        usuario.setTelefono(entity.getTelefono());
        usuario.setNombre(entity.getNombre());
        usuario.setApellidoPaterno(entity.getApellidoPaterno());
        usuario.setApellidoMaterno(entity.getApellidoMaterno());
        return usuario;
    }

    @Override
    public List<Usuario> dtoListtoEntityList(List<UsuarioAcces> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioAcces> entityListtoDTOList(List<Usuario> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
