package com.android.contractorshare.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;

import java.util.Calendar;


public class CalendarHandler {

    public static Intent createEvent(Context context) {

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2016, 6, 19, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2016, 6, 19, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, "Yoga")
                .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com");
        return intent;
    }

}
