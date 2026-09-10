package com.hrm.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private LoggerUtil() {
    }

    // lấy log theo class
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    //lấy log theo name
    public static Logger getLogger(String name) {
        return LogManager.getLogger(name);
    }

}
