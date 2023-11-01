/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.validaciones;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
/**
 *
 * @author juan
 */
public class Validacion {
    
    private Validacion(){};
    
    private static String miVariable="SuperKey12345678";

    
    private static String regex="^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{8,16}$";
    
    public static boolean passwordValidation(String password){
        if(password.length()>7 && password.length()<16){
            return regexValidation(password);
        }else{
            return false;
        }
    }
    
    public static boolean regexValidation(String password){
        return Pattern.matches(regex, password);
    }
    
    public static boolean edadValidation(int edad){
        return edad>=18?true:false;
    }
    
    public static boolean telefonoValidation(String telefono){
        return telefono.length()==10?true:false;
    }
    
    private static SecretKeySpec crearClave() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] claveEncriptacion = miVariable.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        claveEncriptacion = sha.digest(claveEncriptacion);
        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);
        SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, "AES");
        return secretKey;
    }
    
    public static String encriptar(String password) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException{
        SecretKeySpec secretKey = crearClave();
        Cipher cipher = Cipher.getInstance("AES");        
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] datosEncriptar = password.getBytes("UTF-8");
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);
        return encriptado;
    }
    
    public static String desencriptar(String passwordEncriptada) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        SecretKeySpec secretKey = crearClave();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytesEncriptados = Base64.getDecoder().decode(passwordEncriptada);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        String datos = new String(datosDesencriptados);
        return datos;
        
    }
    
    public static Date StringtoDate(String fecha){
        String formato="yyyy/MM/dd";
        Date date=null;
        try{
            date=new SimpleDateFormat(formato).parse(fecha);
        }catch(ParseException ex){
            ex.getMessage();
        }
        return date;
    }
    
    public static String DatetoSring(Date date){
        String formato="dd/MM/yyyy";
        SimpleDateFormat formato_fecha=new SimpleDateFormat(formato);
        return formato_fecha.format(date);
    }
    
    public static String dateValidation(String date){
        String newFormat=null;
        if(date.length()==10){
            String [] parts=date.split("/");
            if(parts.length==3){
                try{
                    int day=Integer.parseInt(parts[0]);
                    int mont=Integer.parseInt(parts[1]);
                    int year=Integer.parseInt(parts[2]);
                    if((day>0&&day<32)&&(mont>0&&mont<13)&&year>1800){
                        if(day==29 && mont==2 && year%4!=0){
                            newFormat="Invalid Date.";
                        }else{
                            newFormat=year+"/"+mont+"/"+day;
                        }
                    }
                }catch(Exception ex){
                    newFormat="Invalid Date.";
                }
            }
        }
        return newFormat;
    }
    
    public static boolean montoValidation(BigDecimal monto){
        if((monto.compareTo(new BigDecimal("0"))>0)&&(monto.compareTo(new BigDecimal("10000000"))<0)){
            return true;
        }else{
            return false;
        }
    }
}
