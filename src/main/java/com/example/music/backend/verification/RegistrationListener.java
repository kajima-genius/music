package com.example.music.backend.verification;

import com.example.music.backend.user.domain.User;
import com.example.music.backend.verification.service.VerificationTokenService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private MessageSource messages;
    private JavaMailSender mailSender;
    private VerificationTokenService tokenService;

    public RegistrationListener(MessageSource messages, JavaMailSender mailSender,
                                VerificationTokenService tokenService) {
        this.messages = messages;
        this.mailSender = mailSender;
        this.tokenService = tokenService;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.sendConformationMail(event);
    }

    private void sendConformationMail(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        String message = messages.getMessage("message.regSucc", null, event.getLocale());

        SimpleMailMessage email = createEmail(recipientAddress, subject, message, confirmationUrl);
        mailSender.send(email);
    }

    private SimpleMailMessage createEmail(String recipientAddress, String subject,
                                          String message, String confirmationUrl) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        return email;
    }
}
