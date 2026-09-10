package com.hrm.config;

import com.hrm.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private final Logger log = LoggerUtil.getLogger(ConfigReader.class);
    private final Properties properties;
    private static ConfigReader instance;

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public ConfigReader() {
        properties = new Properties();
        loadproperties();
        log.info("Load Config Success");
    }

    private void loadproperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input != null) {
                properties.load(input);
                log.debug("Loaded config.properties with {} entries", properties.size());
            } else {
                log.warn("config.properties not found in classpath");
            }
        } catch (IOException e) {
            log.error("Failed to load config.properties", e);
        }
    }

    public String getProperty(String key) {
        // System environment variable
        String envValue = System.getenv(key.toUpperCase().replace('.', '_'));
        if (envValue != null && !envValue.isEmpty()) {
            return envValue;
        }
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        String value = getProperty(key);
        return (value != null && !value.isEmpty()) ? value : defaultValue;
    }
    public String getBaseUrl(){
        return getProperty("base.url","");
    }
    public String getBrowser(){
        return getProperty("browser","false");
    }
    public String isHeadless(){
        return getProperty("headless","");
    }
    public int getImplicitWait(){
        return Integer.parseInt(getProperty("implicit.wait","10"));
    }
    public int getExplicitWait(){
        return Integer.parseInt(getProperty("explicit.wait","10"));
    }
    public int getPageLoadTimeout(){
        return Integer.parseInt(getProperty("page.load.timeout","30"));
    }
    public String getEmail(){
        return getProperty("test.email","");
    }
    public String getPassword(){
        return getProperty("test.password","");
    }
    public String addCompany(){
        return getProperty("add.company","");
    }
    public String addPhone(){
        return getProperty("add.phone","");
    }
    public String addGroupSearch(){
        return getProperty("add.groupSearch","");
    }
    public String addAddress(){
        return getProperty("add.address","");
    }
    public String addCity(){
        return getProperty("add.city","");
    }
    public String customerSearch(){
        return getProperty("modify.customerSearch","");
    }
    public String modifyAddress(){
        return getProperty("modify.address","");
    }
    public String deletecustomer(){
        return getProperty("delete.customerSearch","");
    }

    public String addProjectName(){
        return getProperty("add.projectName","");
    }
    public String addStartDate(){
        return getProperty("add.startDate","");
    }
    public String addDeadline(){
        return getProperty("add.deadline","");
    }
    public String deleteProjectSearch(){
        return getProperty("delete.projectSearch","");
    }
    public String getScreenshotDirectory() {
        return getProperty("screenshot.directory", "screenshots");
    }
}
