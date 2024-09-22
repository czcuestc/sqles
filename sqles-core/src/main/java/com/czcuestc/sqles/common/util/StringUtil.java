package com.czcuestc.sqles.common.util;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class StringUtil {
    private static final char UNDERLINE = '_';
    private static final char PERCENT = '%';

    private static final char BLANK = ' ';

    public static boolean isEmpty(String value) {
        return (value == null || value.length() == 0);
    }

    public static String[] split(char c, String v) {
        return v.split(String.valueOf(c));
    }

    public static String[] split(String v) {
        return v.split(",");
    }

    public static String concat(Object... values) {
        if (values == null || values.length == 0) return null;
        StringBuilder stringBuilder = new StringBuilder();
        for (Object v : values) {
            stringBuilder.append(v).append(BLANK);
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static String format(String message, Object... values) {
        if (StringUtil.isEmpty(message)) return message;

        int index = -1;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == '{') {
                i++;
                c = message.charAt(i);
                if (c == '}') {
                    index++;
                    stringBuilder.append(values[index]);
                }
            } else {
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public static String underlineToCamel(String strValue) {
        if (strValue == null || "".equals(strValue.trim())) {
            return "";
        }
        int len = strValue.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = strValue.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(strValue.charAt(i)));
                }
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }

    public static String camelToUnderline(String camelCase) {
        if (camelCase == null || "".equals(camelCase.trim())) {
            return "";
        }

        int len = camelCase.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = camelCase.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) {
                    result.append(UNDERLINE).append(Character.toLowerCase(c));
                } else {
                    result.append(Character.toLowerCase(c));
                }
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static boolean startsWithIgnoreCase(String stringValue, String startWithString) {
        if (isEmpty(stringValue)) return false;

        return stringValue.toLowerCase().startsWith(startWithString);
    }

    public static boolean isPrefix(String queryString) {
        if (isEmpty(queryString)) return false;

        char endChar = queryString.charAt(queryString.length() - 1);
        if (endChar == PERCENT) {
            for (int index = 0; index < queryString.length() - 1; index++) {
                char c = queryString.charAt(index);
                if (c == UNDERLINE || c == PERCENT)
                    return false;
            }

            if (queryString.length() == 1) {
                return false;
            }
            return true;
        }

        return false;
    }

    public static String getPrefixQueryString(String queryString) {
        return queryString.substring(0, queryString.length() - 1);
    }
}
