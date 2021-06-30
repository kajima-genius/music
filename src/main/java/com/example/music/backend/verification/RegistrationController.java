package com.example.music.backend.verification;

import com.example.music.backend.user.domain.User;
import com.example.music.backend.user.service.UserService;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Calendar;
import java.util.Locale;

@RestController
public class RegistrationController {

    private UserService userService;

    private MessageSource messages;

    public RegistrationController(UserService userService, MessageSource messages) {
        this.userService = userService;
        this.messages = messages;
    }

    @GetMapping("/registrationConfirm.html")
    public String confirmRegistration
            (WebRequest request, Model model, @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.getVerificationToken(token);

        if(verificationToken == null) {
            String message = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if(verificationToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
    }

}
