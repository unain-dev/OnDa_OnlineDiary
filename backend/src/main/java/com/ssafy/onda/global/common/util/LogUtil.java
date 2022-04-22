package com.ssafy.onda.global.common.util;

public class LogUtil {

    public static String getClassName() {
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public static String getElement() {
        return Thread.currentThread().getStackTrace()[2].toString();
    }

    public static String getClassAndMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[2].getClassName();
        String methodName = stackTrace[2].getMethodName();
        return className + " > " + methodName;
    }
}
