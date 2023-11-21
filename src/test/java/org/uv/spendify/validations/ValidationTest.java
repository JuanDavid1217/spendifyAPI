/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.validations;
import org.uv.spendify.validations.Validation;
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
public class ValidationTest {
    @Test
    void checkPasswordValidation(){
        assertEquals(Validation.passwordValidation("Juan"), false);
        assertEquals(Validation.passwordValidation("Juan#123456789107"), false);
        assertEquals(Validation.passwordValidation("Juan#123"), true);
    }
    
    @Test
    void checkEdadValidation(){
        assertEquals(Validation.edadValidation(17), false);
        assertEquals(Validation.edadValidation(18), true);
    }
    
    @Test
    void checkTelefonoValidation(){
        assertEquals(Validation.telefonoValidation("123456789"), false);
        assertEquals(Validation.telefonoValidation("1234567890"), true);
        assertEquals(Validation.telefonoValidation("12345678901"), false);
    }
    
    @Test
    void checkEncriptar() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException{
        assertEquals(Validation.encriptar("Juan#123"), "fTfk8EciST0BpxvtH1qwBQ==");
    }
    
    @Test
    void checkDesencriptar() throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException{
        assertEquals(Validation.desencriptar("fTfk8EciST0BpxvtH1qwBQ=="), "Juan#123");
    }
    
    @Test
    void checkDateValidation(){
        assertEquals(Validation.dateValidation("2/11/2023"), "2023/11/2");
        assertEquals(Validation.dateValidation("02/11/2023"), "2023/11/2");
        assertEquals(Validation.dateValidation("la/11/2023"), "Invalid Date.");
        assertEquals(Validation.dateValidation("29/02/2023"), "Invalid Date.");
        assertEquals(Validation.dateValidation("29/02/2020"), "2020/2/29");
        assertEquals(Validation.dateValidation("029/002/2020"), null);
    }
    
    @Test
    void checkMontoValidation(){
        assertEquals(Validation.montoValidation(new BigDecimal(0)), false);
        assertEquals(Validation.montoValidation(new BigDecimal(9850)), true);
        assertEquals(Validation.montoValidation(new BigDecimal(10000000)), false);
    }
    
    @Test
    void checkUmbralValidation(){
        assertEquals(Validation.umbralValidation(-5.32), false);
        assertEquals(Validation.umbralValidation(100.32), false);
        assertEquals(Validation.umbralValidation(100), true);
    }
    
    @Test
    void checkIsTokenValid(){
        assertEquals(Validation.isTokenValid(LocalDateTime.now()), true);
        assertEquals(Validation.isTokenValid(LocalDateTime.parse("2023-11-10T02:09:43.867902")), false);
    }
}
