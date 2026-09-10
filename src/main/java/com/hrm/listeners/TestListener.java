package com.hrm.listeners;

import com.hrm.drivers.DriverSetUpTest;
import com.hrm.utils.LoggerUtil;
import com.hrm.utils.ScreenshotUtil;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private final Logger log = LoggerUtil.getLogger(TestListener.class);
    private WebDriver driver;

    @Override
    public void onStart(ITestContext context) {
        log.info(" Test Suite — Bắt đâu", context.getClass());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info(" Test Suite — Kết thúc", context.getClass());
        log.info("Pass: {}|Fail:{}|Skipped:{}",
                context.getPassedTests().size(), context.getFailedTests().size(), context.getSkippedTests().size());
    }

    @Override
    public void onTestStart(ITestResult result) {
        log.info(" Test Start: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info(" Test PASS: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.error(" Test FAIL: {}", result.getMethod().getMethodName());
        log.error("Reason: {}", result.getThrowable().getMessage());
        try {
            driver = DriverSetUpTest.getDriver();
            ScreenshotUtil.capturAndSave(driver, result.getMethod().getMethodName());
        } catch (Exception e) {
            log.warn("Không chup đươc Sceenshot: {}", e.getMessage());

        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.warn(" Test SKIPPED: {}", result.getMethod().getMethodName());
    }
}

