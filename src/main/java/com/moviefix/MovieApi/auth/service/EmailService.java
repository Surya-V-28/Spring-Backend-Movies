package com.moviefix.MovieApi.auth.service;


import com.moviefix.MovieApi.dto.MailBody;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private  final JavaMailSender javaMailSender;
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMessage(MailBody mailBody) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to().trim());
        message.setSubject(mailBody.Subject());
        message.setFrom("startup.work.231@gmail.com");
        message.setText(mailBody.message());
        javaMailSender.send(message);
    }
}
