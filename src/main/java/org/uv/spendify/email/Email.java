/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.spendify.email;

import java.io.File;
import java.nio.charset.StandardCharsets;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 *
 * @author juan
 */
@Component
public class Email {
    
    @Value("${email.sender}")
    private String emailUser;
    
    private final JavaMailSender mailSender;
    
    public Email(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }
    
    public void sendEmail(String toUser, String subject, String message){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(emailUser);
        mailMessage.setTo(toUser);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
    
    public void sendEmailWithFile(String toUser, String subject, String message, File file) throws MessagingException{
        MimeMessage mimeMessage=mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
        
        mimeMessageHelper.setFrom(emailUser);
        mimeMessageHelper.setTo(toUser);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);
        mimeMessageHelper.addAttachment(file.getName(), file);
        
        mailSender.send(mimeMessage);
    }
}
