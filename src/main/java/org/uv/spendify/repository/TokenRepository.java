/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.uv.spendify.models.Token;

/**
 *
 * @author juan
 */
public interface TokenRepository extends JpaRepository<Token, Long>{
    public Token findByClave(String clave);
    
    @Query(value="SELECT * FROM tokens WHERE id_usuario=:idUsuario", nativeQuery=true)
    public Token findByIdUsuario(long idUsuario);
}
