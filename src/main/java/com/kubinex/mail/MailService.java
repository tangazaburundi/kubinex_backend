package com.kubinex.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final String from;
    private final String to;

    public MailService(JavaMailSender mailSender,
                       @Value("${app.mail.from}") String from,
                       @Value("${app.mail.to}") String to) {
        this.mailSender = mailSender;
        this.from = from;
        this.to = to;
    }

    public void sendContact(String name, String email, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(from);
        mail.setTo(to);
        mail.setReplyTo(email);
        mail.setSubject("Kubinex — Nouveau message de " + name + " : " + subject);
        mail.setText("""
                Nouveau message depuis le formulaire de contact Kubinex.

                Nom : %s
                Email : %s
                Sujet : %s

                Message :
                %s
                """.formatted(name, email, subject, message));
        mailSender.send(mail);
    }
}
