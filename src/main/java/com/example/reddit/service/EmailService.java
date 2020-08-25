package com.example.reddit.service;

import com.example.reddit.DTO.AuthenticationMail;
import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public String sendMail(AuthenticationMail authenticationMail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        try {
            messageHelper.setFrom("adilmeric13@gmail.com");
            messageHelper.setTo(authenticationMail.getRecipient());
            messageHelper.setText(authenticationMail.getContent());
            messageHelper.setSubject(authenticationMail.getSubject());
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error...";
        }
        mailSender.send(mimeMessage);
        return "Mail Sent!";
    }
}
