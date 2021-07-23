package com.example.music.backend.verification.domain;

import java.util.Calendar;
import java.util.Date;

public class VerificationTokenUtil {

    private static final int EXPIRATION = 60 * 24;

    public static Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, EXPIRATION);
        return cal.getTime();
    }
}
