package com.android.contractorshare.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.android.contractorshare.R;

public class ProfileHandler {

    public static Drawable get(int userId, Context context) {
        switch (userId) {
            case 36:
                return ContextCompat.getDrawable(context, R.drawable.ic_profile_roger_2);
            case 41:
                return ContextCompat.getDrawable(context, R.drawable.ic_profile_olga_2);
            default:
                return ContextCompat.getDrawable(context, R.drawable.ic_profile);
        }
    }
}
