package com.turkraft.springfilter;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * 勿删此类，
 * 为了覆盖 spring-filter jar 包里的 FilterParameters 类。时间格式按中国常用。
 * spring-filter 回头直接整工程里
 */
public class FilterParameters {

    private FilterParameters() {}

    public static SimpleDateFormat DATE_FORMATTER;
    public static DateTimeFormatter LOCALDATE_FORMATTER;
    public static DateTimeFormatter LOCALDATETIME_FORMATTER;
    public static DateTimeFormatter OFFSETDATETIME_FORMATTER;
    public static DateTimeFormatter LOCALTIME_FORMATTER;
    public static boolean CASE_SENSITIVE_LIKE_OPERATOR;

    public static FilterFunction[] CUSTOM_FUNCTIONS;

    static {

        DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
        LOCALDATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LOCALDATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        OFFSETDATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LOCALTIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
        CASE_SENSITIVE_LIKE_OPERATOR = false;
        CUSTOM_FUNCTIONS = null;
    }

}
