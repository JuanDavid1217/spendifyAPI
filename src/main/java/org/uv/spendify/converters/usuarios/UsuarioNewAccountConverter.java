/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.converters.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.spendify.dtos.usuarios.UsuarioNewAccount;
import org.uv.spendify.converters.Converter;
import org.uv.spendify.models.Usuario;

/**
 *
 * @author juan
 */
@Component
public class UsuarioNewAccountConverter implements Converter<Usuario, UsuarioNewAccount>{

    @Override
    public Usuario dtotoEntity(UsuarioNewAccount dto) {
        Usuario usuario=new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setEdad(dto.getEdad());
        usuario.setPassword(dto.getPassword());
        usuario.setTelefono(dto.getTelefono());
        usuario.setNombre(dto.getNombre());
        usuario.setApellidoPaterno(dto.getApellidoPaterno());
        usuario.setApellidoMaterno(dto.getApellidoMaterno());
        return usuario;
    }

    @Override
    public UsuarioNewAccount entitytoDTO(Usuario entity) {
        UsuarioNewAccount usuario=new UsuarioNewAccount();
        usuario.setEdad(entity.getEdad());
        usuario.setEmail(entity.getEmail());
        usuario.setPassword(entity.getPassword());
        usuario.setTelefono(entity.getPassword());
        usuario.setNombre(entity.getNombre());
        usuario.setApellidoPaterno(entity.getApellidoPaterno());
        usuario.setApellidoMaterno(entity.getApellidoMaterno());
        return usuario;
    }

    @Override
    public List<Usuario> dtoListtoEntityList(List<UsuarioNewAccount> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }

    @Override
    public List<UsuarioNewAccount> entityListtoDTOList(List<Usuario> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
