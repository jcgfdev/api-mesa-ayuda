package com.enyoi.apimesaayuda.security.services.impl;

import com.enyoi.apimesaayuda.base.exceptions.EmailException;
import com.enyoi.apimesaayuda.security.services.IEmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String correo;

    @Override
    @Async
    public void enviar(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Activacion Usuario!!:)");
            helper.setFrom(correo);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("fallo envio de correo: " + e.getMessage());
            throw new EmailException("error al enviar correo");
        }
    }
}
