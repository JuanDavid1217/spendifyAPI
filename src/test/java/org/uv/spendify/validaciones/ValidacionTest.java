/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.validaciones;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 *
 * @author juan
 */
public class ValidacionTest {
    @Test
    void checkPasswordValidation(){
        assertEquals(Validacion.passwordValidation("Juan"), false);
        assertEquals(Validacion.passwordValidation("Juan#123456789107"), false);
        assertEquals(Validacion.passwordValidation("Juan#123"), true);
    }
    
    @Test
    void checkEdadValidation(){
        assertEquals(Validacion.edadValidation(17), false);
        assertEquals(Validacion.edadValidation(18), true);
    }
    
    @Test
    void checkTelefonoValidation(){
        assertEquals(Validacion.telefonoValidation("123456789"), false);
        assertEquals(Validacion.telefonoValidation("1234567890"), true);
        assertEquals(Validacion.telefonoValidation("12345678901"), false);
    }
    
    @Test
    void checkEncriptar() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException{
        assertEquals(Validacion.encriptar("Juan#123"), "fTfk8EciST0BpxvtH1qwBQ==");
    }
    
    @Test
    void checkDesencriptar() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException{
        assertEquals(Validacion.desencriptar("fTfk8EciST0BpxvtH1qwBQ=="), "Juan#123");
    }
    
    @Test
    void checkDateValidation(){
        assertEquals(Validacion.dateValidation("2/11/2023"), "2023/11/2");
        assertEquals(Validacion.dateValidation("02/11/2023"), "2023/11/2");
        assertEquals(Validacion.dateValidation("la/11/2023"), "Invalid Date.");
        assertEquals(Validacion.dateValidation("29/02/2023"), "Invalid Date.");
        assertEquals(Validacion.dateValidation("29/02/2020"), "2020/2/29");
        assertEquals(Validacion.dateValidation("029/002/2020"), null);
    }
    
    @Test
    void checkMontoValidation(){
        assertEquals(Validacion.montoValidation(new BigDecimal(0)), false);
        assertEquals(Validacion.montoValidation(new BigDecimal(9850)), true);
        assertEquals(Validacion.montoValidation(new BigDecimal(10000000)), false);
    }
    
    @Test
    void checkUmbralValidation(){
        assertEquals(Validacion.umbralValidation(-5.32), false);
        assertEquals(Validacion.umbralValidation(100.32), false);
        assertEquals(Validacion.umbralValidation(100), true);
    }
    
    @Test
    void checkIsTokenValid(){
        assertEquals(Validacion.isTokenValid(LocalDateTime.now()), true);
        assertEquals(Validacion.isTokenValid(LocalDateTime.parse("2023-11-10T02:09:43.867902")), false);
    }
}
