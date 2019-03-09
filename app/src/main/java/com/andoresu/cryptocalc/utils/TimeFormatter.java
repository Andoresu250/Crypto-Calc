package com.andoresu.cryptocalc.utils;

import android.annotation.SuppressLint;

import java.util.ArrayList;

public class TimeFormatter {

    public final static long ONE_MILLISECOND = 1;
    public final static long MILLISECONDS_IN_A_SECOND = 1000;

    public final static long ONE_SECOND = 1000;
    public final static long SECONDS_IN_A_MINUTE = 60;

    public final static long ONE_MINUTE = ONE_SECOND * 60;
    public final static long MINUTES_IN_AN_HOUR = 60;

    public final static long ONE_HOUR = ONE_MINUTE * 60;
    public final static long HOURS_IN_A_DAY = 24;
    public final static long ONE_DAY = ONE_HOUR * 24;
    public final static long DAYS_IN_A_YEAR = 365;



    @SuppressLint("DefaultLocale")
    public static String format(long durationInSeconds) {

        long duration = durationInSeconds * 1000;

        duration /= ONE_MILLISECOND;
        int milliseconds = (int) (duration % MILLISECONDS_IN_A_SECOND);
        duration /= ONE_SECOND;
        int seconds = (int) (duration % SECONDS_IN_A_MINUTE);
        duration /= SECONDS_IN_A_MINUTE;
        int minutes = (int) (duration % MINUTES_IN_AN_HOUR);
        duration /= MINUTES_IN_AN_HOUR;
        int hours = (int) (duration % HOURS_IN_A_DAY);
        duration /= HOURS_IN_A_DAY;
        int days = (int) (duration % DAYS_IN_A_YEAR);
        duration /= DAYS_IN_A_YEAR;
        int years = (int) (duration);

        String YEAR_FORMAT = "%s años";
        String DAYS_FORMAT = "%s dias";
        String HOUR_FORMAT = "%s horas";
        String MIN_FORMAT = "%s mins.";
        String SEC_FORMAT = "%s seg.";

        ArrayList<String> paramsList = new ArrayList<>();

        String format = "";

        if(years != 0){
            format += YEAR_FORMAT + " ";
            paramsList.add(years + "");
        }
        if(days != 0){
            format += DAYS_FORMAT + " ";
            paramsList.add(days + "");
        }
        if(hours != 0){
            format += HOUR_FORMAT + " ";
            paramsList.add(hours + "");
        }
        if(minutes != 0){
            format += MIN_FORMAT + " ";
            paramsList.add(minutes + "");
        }
        if(seconds != 0){
            format += SEC_FORMAT + " ";
            paramsList.add(seconds + "");
        }

        String[] params = paramsList.toArray(new String[paramsList.size()]);

        return String.format(format, params);

    }

}
