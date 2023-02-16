package com.hetun.datacenter.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Integer now() {
        return Math.toIntExact(System.currentTimeMillis() / 1000);
    }

    public static boolean isToday(long time) {
        return isThisTime(time*1000, "yyyy-MM-dd");
    }

    private static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        return param.equals(now);
    }
}
