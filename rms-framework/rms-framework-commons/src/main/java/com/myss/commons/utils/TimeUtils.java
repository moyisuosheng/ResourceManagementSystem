package com.myss.commons.utils;

/**
 * 时间 工具
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
public class TimeUtils {
    /**
     * 时间 工具
     */
    private TimeUtils() {
        throw new IllegalStateException("TimeUtils class");
    }

    /**
     * 秒转为时间
     *
     * @param times 次
     * @return {@link String}
     */
    public static String secondToTime(Double times) {
        if (times < 0) {
            throw new IllegalArgumentException("Seconds must be a positive number!");
        }
        int seconds = times.intValue();
        int hour = seconds / 3600;
        int other = seconds % 3600;
        int minute = other / 60;
        int second = other % 60;
        final StringBuilder sb = new StringBuilder();
        if (hour > 0) {
            sb.append(hour);
            sb.append(":");
        }
        if (minute < 10) {
            sb.append("0");
        }
        sb.append(minute);
        sb.append(":");
        if (second < 10) {
            sb.append("0");
        }
        sb.append(second);
        return sb.toString();
    }
}
