package com.example.music.backend.verification.domain;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class VerificationTokenUtil {

    private static final int EXPIRATION = 60 * 24;

    public static Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        return new Date(cal.getTime().getTime());
    }
}
