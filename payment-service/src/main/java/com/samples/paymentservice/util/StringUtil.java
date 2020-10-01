package com.samples.paymentservice.util;

/**
 * @author Elham
 * @since 10/1/2020
 */
public class StringUtil {

    public static String separatePan(String value) {
        return StringUtil.addSeparator(value, 4, '-');
    }

    public static String addSeparator(String value, int interval, char separator) {
        StringBuilder sb = new StringBuilder(value);

        for (int i = 0; i < value.length() / interval; i++) {
            sb.insert(((i + 1) * interval) + i, separator);
        }
        if (sb.charAt(sb.length() - 1) == separator) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }
}
