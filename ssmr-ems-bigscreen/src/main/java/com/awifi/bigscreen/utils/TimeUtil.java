package com.awifi.bigscreen.utils;

import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateUtils;
import org.roof.commons.RoofDateUtils;

public class TimeUtil {
	public static final int SECONDS_IN_HOUR = 60 * 60;
    public static final long MILLIS_IN_HOUR = 1000L * SECONDS_IN_HOUR;
    
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;
    
    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(ms1) == toDay(ms2);
    }

    public static boolean isSameHourOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_HOUR
                && interval > -1L * MILLIS_IN_HOUR;
    }
    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }
    
    public static void main(String[] s) {
    	Date date1 = DateUtils.addMinutes(new Date(), 2);
    	Date date2 = new Date();
    	System.out.println(isSameHourOfMillis(date1.getTime(), date2.getTime()));
	}

}
