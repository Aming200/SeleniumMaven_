package com.hrm.base;

import com.hrm.config.ConfigReader;
import com.hrm.drivers.DriverSetUpTest;
import com.hrm.utils.LoggerUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {
    protected final Logger log = LoggerUtil.getLogger(this.getClass());
    protected WebDriver driver;


    @BeforeSuite()
    public void beforeSuite(){
        log.info("  Automation Test Suite — Bắt đâu");
        log.info("  Browser:  {}", ConfigReader.getInstance().getBrowser());
        log.info("  Base URL: {}", ConfigReader.getInstance().getBaseUrl());
    }
    @BeforeMethod()
    public void setUp(){
        driver= DriverSetUpTest.setDriver();
        String baseUrl= ConfigReader.getInstance().getBaseUrl();
        log.info("Navigate đến base URL: {}",baseUrl);
        driver.navigate().to(baseUrl);
    }
//    @Test
//    public void testThu() {
//        log.info("Đang chạy test case...");
//    }
    @AfterMethod()
    public void tearDown(ITestResult result) throws Exception {
        DriverSetUpTest.tearDown();
    }
    @AfterSuite()
    public void afterSuite(){
        log.info("Automation Test Suite — Kêt thúc");
    }
    protected WebDriver getDriver() {
        return driver;
    }
}

