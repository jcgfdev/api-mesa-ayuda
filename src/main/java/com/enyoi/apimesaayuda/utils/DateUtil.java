package com.enyoi.apimesaayuda.utils;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Service
public class DateUtil {

    public TimeZone timeZone() {
        return TimeZone.getTimeZone("America/Bogota");
    }

    /**
     * yyyy-MM-dd'T'HH:mm:ss
     */
    public SimpleDateFormat dateFormat() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormat.setTimeZone(timeZone());
        return dateFormat;
    }
}
