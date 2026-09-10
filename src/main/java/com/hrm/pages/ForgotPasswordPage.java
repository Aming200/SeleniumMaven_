package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage {

    private By emailInput = By.id("email");
    private By confirmButton = By.cssSelector("button[type='submit'].btn-primary");
    private By pageHeading = By.cssSelector("h1");
    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }
    public boolean isPageLoaded() {
        return isDisplayed(emailInput) && isDisplayed(confirmButton);
    }
    public String getPageHeadingText() {
        return  getText(pageHeading);
    }
    public String getCurrentPageUrl() {
    return getCurrentUrl();
    }
    public void fillFormForgotPass(String email){
        type(emailInput,email);
        click(confirmButton);
    }
}
