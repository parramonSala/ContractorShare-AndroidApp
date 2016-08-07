package com.android.contractorshare.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class DateHandler {

    public static String fromWCFToAndroidDateConverter(String date) {
        Calendar calendar = Calendar.getInstance();
        String ackwardRipOff = date.replace("/Date(", "").replace(")/", "");
        Long timeInMillis;
        if (ackwardRipOff.contains("+")) {
            timeInMillis = Long.valueOf(ackwardRipOff.substring(0, ackwardRipOff.indexOf("+")));
        } else timeInMillis = Long.valueOf(ackwardRipOff);
        calendar.setTimeInMillis(timeInMillis);
        return calendar.getTime().toString();
    }

    public static String fromAndroidtoWCFDateConverter(Date dt) {
        final Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
        cal.setTime(dt); // Where Value is a Date
        final long date = cal.getTime().getTime();
        final String senddate = "/Date(" + date + ")/";
        return senddate;
    }
}
