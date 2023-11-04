/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.exceptions;

import org.springframework.http.HttpStatus;

/**
 *
 * @author juan
 */
public class Exceptions extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;
    
    public Exceptions (String message, HttpStatus httpStatus){
        super(message);
        this.message=message;
        this.httpStatus=httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    
    
}
