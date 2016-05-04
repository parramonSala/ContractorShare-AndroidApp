package com.android.contractorshare.utils;

import java.util.Calendar;

/**
 * Created by Roger on 04/05/2016.
 */
public class DateHandler {

    public static String dateConverter(String date) {
        Calendar calendar = Calendar.getInstance();
        String ackwardRipOff = date.replace("/Date(", "").replace(")/", "");
        Long timeInMillis = Long.valueOf(ackwardRipOff.substring(0, ackwardRipOff.indexOf("+")));
        calendar.setTimeInMillis(timeInMillis);
        return calendar.getTime().toString();
    }
}
