package com.hrm.pages;

import com.hrm.utils.LoggerUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Alert;

import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.Logger;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private JavascriptExecutor js;
    private Actions action;
    protected final Logger log;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        action = new Actions(driver);
        log = LoggerUtil.getLogger(this.getClass());
    }

    protected void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected boolean verifyPageText(By locator, String text) {
        waitForVisible(locator);
        log.debug("Page text: {} ",driver.findElement(locator).getText());
        System.out.println("Page text " + driver.findElement(locator).getText());
        return driver.findElement(locator).equals(text);
    }

    protected void click(By locator) {
        log.debug("Clicking element: {}", locator);
        waitForVisible(locator);
        waitForClickable(locator);
        driver.findElement(locator).click();

    }

    protected void type(By locator, String text) {
        log.debug("Typing '{}' into: {}", text, locator);
        waitForVisible(locator);
        driver.findElement(locator).click();
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }
    public void clearField(By locator) {
        waitForVisible(locator);
        WebElement element = driver.findElement(locator);
        element.clear();
    }

    protected String getText(By locator) {
        waitForVisible(locator);
        String text = driver.findElement(locator).getText();
        log.debug("Got text '{}' from: {}", text, locator);
        return text;
    }

    //xử lý alert
    protected void dimissAlert() {
        log.debug("Click dimiss");
        driver.switchTo().alert().dismiss();
    }

    protected void acceptAlert() {
        log.debug("Click accept");
        driver.switchTo().alert().accept();
    }

    protected void getextAlert() {
        log.debug("lấy text từ Alert");
        driver.switchTo().alert().getText();
    }

    protected void sendkeysAlert(String value) {
        log.debug("Sendkey: {}", value);
        driver.switchTo().alert().sendKeys(value);
    }

    protected String getAttribute(By locator, String attribute) {
        waitForVisible(locator);
        WebElement element = driver.findElement(locator);
        return element.getAttribute(attribute);

    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();

        } catch (Exception e) {
            return false;
        }
    }


    protected boolean isChecked(By locator) {
        try {
            return driver.findElement(locator).isSelected();

        } catch (Exception e) {
            return false;
        }
    }

    // để lấy giá trị trong dropdown theo text
    protected void selectByVisibleText(By locator, String text) {
        waitForVisible(locator);
        WebElement element = driver.findElement(locator);
        log.debug("Selecting '{}' from dropdown: {}", text, locator);
        Select select = new Select(element);
        select.selectByVisibleText(text);

    }

    // để lấy giá trị trong dropdown theo Value
    protected void selectByValue(By locator, String value) {
        waitForVisible(locator);
        WebElement element = driver.findElement(locator);
        log.debug("Selecting value '{}' from dropdown: {}", value, locator);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    protected void selectByIndex(By locator, int index) {
        waitForVisible(locator);
        WebElement element = driver.findElement(locator);
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    //dropdown động search đúng giá trị
    protected void selectDynamicDropdown(By inputDropdown, String value) {
        waitForVisible(inputDropdown);
        driver.findElement(inputDropdown).sendKeys(value);
        action.sendKeys(Keys.ENTER).build().perform();

    }

    //dropdown động chọn từ tìm kiếm từ thanh cuộn giá trị dropdown
    protected void selectDynamicDropdown(By locatorList, By locator, String value) throws InterruptedException {
        Thread.sleep(2000);
        waitForVisible(locatorList);
        List<WebElement> lists = driver.findElements(locatorList);
        for (int i = 0; i < lists.size(); i++) {
            WebElement detail = lists.get(i);
            WebElement detailText = detail.findElement(locator);
            if (detailText.getText().toUpperCase().contains(value.toUpperCase())) {
                log.debug("Selecting element {}", detailText);
                System.out.println(detailText);
                log.debug("Selecting value {}", detailText.getText());
                System.out.println(detailText.getText());
                detailText.click();
                break;
            }

        }
    }

    protected int getElementCount(By locator) {

        return driver.findElements(locator).size();
    }

    // Hàm nay nếu để trả về sẽ tai sử dung đươc cho element nó timf visibale hay không
    // nêu không để trả về bên trên có ham click senkeys sẽ gọi trực tiếp ham này lên dung luôn
    protected void waitForVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void waitForClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));

    }

    protected void waitForInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

    }

    protected void waitForUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));

    }

    protected void waitForTitleContains(String titlePart) {
        wait.until(ExpectedConditions.titleContains(titlePart));

    }

    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        log.debug("Scrolled to element: {}", locator);

    }

    protected void moveToElementL(By locator) {
        waitForVisible(locator);
        action.moveToElement(driver.findElement(locator)).build().perform();
        log.debug("Move to element: {}", locator);

    }

    protected void moveToElementE(WebElement element) {
        action.moveToElement(element).build().perform();
        log.debug("Move to element: {}", element);

    }

    protected void actionEnter() {
        action.sendKeys(Keys.ENTER).build().perform();
    }

    protected void enterSearchValue(By input_Search, String value) {
        type(input_Search, value);
        actionEnter();
    }

    protected void jsClickL(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        log.debug("JS clicked element: {}", locator);

    }

    protected void jsClickE(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        log.debug("JS clicked element: {}", element);

    }

    protected void actionSenkeys(By locator, String value) {

        action.click(driver.findElement(locator))
                .sendKeys(value)
                .perform();
    }

    protected Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) driver).executeScript(script, args);
    }
}
