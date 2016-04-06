package com.android.contractorshare.utils;

public class StatusHandler {

    public static String getStatusText(int statusId) {
        switch (statusId) {
            case 1:
                return "Open";
            case 2:
                return "In Progress";
            case 3:
                return "Completed";
            case 4:
                return "Cancelled";
            case 5:
                return "Accepted";
            case 6:
                return "Rejected";
            case 7:
                return "Closed";
            default:
                return "null";
        }
    }
}
