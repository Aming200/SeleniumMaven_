package com.hrm.drivers;

import com.hrm.config.ConfigReader;
import com.hrm.utils.LoggerUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.time.Duration;

public class DriverSetUpTest {
    private static WebDriver driver;
    private static final Logger log = LoggerUtil.getLogger(DriverSetUpTest.class);
    public static WebDriver getDriver() {
//        if (driver==null){
//            throw new IllegalStateException (
//                    "WebDriver not initialized. Call DriverSetUpTest.setDriver first.");
//        }
        return driver;
    }

    public static WebDriver setDriver() {
        ConfigReader config = ConfigReader.getInstance();
        String browserType= config.getBrowser().toLowerCase();
        switch (browserType) {
            case "chrome": {
                driver = initChromeDriver();
                break;
            }
            case "firefox": {
                driver = initFirefoxDriver();
                break;
            }
            default:
                driver = initChromeDriver();
        }
        configureDriver(driver);
        log.info("Khởi tạo thành công "+ browserType);
        return driver;
    }

    private static WebDriver initChromeDriver() {
        boolean headless = false;
        log.info("Khởi tạo Chrome");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver initFirefoxDriver() {
        log.info("Khởi tao Firefox");
        WebDriverManager.firefoxdriver().setup();
        return driver;
    }
    private static void configureDriver(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(
                Duration.ofSeconds(30));
    }
    @AfterClass
    public static void tearDown() throws Exception {
        if (driver != null) {
            try {
                Thread.sleep(2000);
                driver.quit();
                log.info("Đóng Browser thành công");
            } catch (Exception e) {
                log.error("Đóng Browser thất bại", e);
            }
        }
    }
}
