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

    public static int getStatusInt(String statusText) {
        switch (statusText) {
            case "Open":
                return 1;
            case "In Progress":
                return 2;
            case "Completed":
                return 3;
            case "Cancelled":
                return 4;
            case "Accepted":
                return 5;
            case "Rejected":
                return 6;
            case "Closed":
                return 7;
            default:
                return -1;
        }
    }
}
