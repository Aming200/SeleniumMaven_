package com.hrm.utils;

import com.hrm.config.ConfigReader;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {
    private static final Logger log = LoggerUtil.getLogger(ScreenshotUtil.class);
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtil() {
    }
    public static String captureScreenshot(WebDriver driver, String captureName){
        String screenshotDir= ConfigReader.getInstance().getScreenshotDirectory();
        String timestamp= LocalDateTime.now().format(dateFormat);
        String fileName= captureName+"_"+timestamp+ ".png";
        try {
            Path dirPath= Paths.get(screenshotDir);
            Files.createDirectories(dirPath);

            byte[] screenshotBytes= ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Path filePath=dirPath.resolve(fileName);
            Files.write(filePath,screenshotBytes);
//            log.info("Save Screenshot: {}", filePath.toAbsolutePath());
            return filePath.toAbsolutePath().toString();


        }catch (IOException e){
            log.error("không lưu được screensshot: {}", captureName, e);
            return null;
        }


    }
    public static  void  attachToAllure(WebDriver driver, String  name){
        try {
            byte[] screenshotBytes= ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name,"image/png", new ByteArrayInputStream(screenshotBytes),".png");
            log.debug("Attach screenshot vào allure report: {}", name);

        }catch (Exception e){
            log.error("Attach screenshot vào allure report Fail ", e);

        }
    }
    public static String capturAndSave (WebDriver driver, String captureName) {
        String filePath = captureScreenshot(driver, captureName);
        attachToAllure(driver,captureName);
        return filePath;
    }
}
