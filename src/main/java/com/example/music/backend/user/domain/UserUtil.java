package com.example.music.backend.user.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserUtil {

    public static Date parseDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");
        try {
            Date date = formatter.parse(strDate);
            return date;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
