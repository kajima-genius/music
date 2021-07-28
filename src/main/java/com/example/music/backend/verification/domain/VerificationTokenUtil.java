package com.example.music.backend.verification.domain;

import lombok.experimental.UtilityClass;

import java.util.Calendar;
import java.util.Date;

@UtilityClass
public class VerificationTokenUtil {

    private final int EXPIRATION = 60 * 24;

    public Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, EXPIRATION);
        return cal.getTime();
    }
}

