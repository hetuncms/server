package com.hetun.datacenter.tools;

public class DateUtils {

    public static Integer now() {
        return Math.toIntExact(System.currentTimeMillis() / 1000);
    }
}
