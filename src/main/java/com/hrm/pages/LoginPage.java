package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    private By emailInput = By.id("email");
    private By passwordInput = By.id("password");
    private By loginButton = By.xpath("//button[@class='btn btn-primary btn-block']");
    private By rememberMeCheckbox = By.id("remember");
    private By rememberMeLabel = By.xpath("//label[@for='remember']");
    private By forgotPasswordLink = By.linkText("Forgot Password?");
    private By alertDanger = By.cssSelector(".alert.alert-danger");
    private By pageHeading = By.xpath("h1");
    private By logoImage = By.cssSelector(".company-logo img");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        return isDisplayed(emailInput) && isDisplayed(passwordInput);
    }

    public boolean isLogoDisplayed() {
        return isDisplayed(logoImage);
    }

    public boolean isEmailFieldDisplayed() {
        return isDisplayed(emailInput);
    }

    public boolean isPasswordFieldDisplayed() {
        return isDisplayed(passwordInput);
    }

    public boolean isLoginButtonDisplayed() {
        return isDisplayed(loginButton);
    }

    public boolean isRememberMeDisplayed() {
        return isDisplayed(rememberMeLabel) || isDisplayed(rememberMeCheckbox);
    }

    public boolean isForgotPasswordDisplayed() {
        return isDisplayed(forgotPasswordLink);
    }

    public boolean isAlertDangerDisplayed() {
        return isDisplayed(alertDanger);
    }

    public void enterEmail(String email) {
        log.info("Entering email: {}", email);
        type(emailInput, email);
    }
    public void enterPassword(String password) {
        log.info("Entering password: [MASKED]");
        type(passwordInput, password);
    }
    public void clickLogin() {
        log.info("Click Login button");
        click(loginButton);
    }
    public DashboardPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLogin();
        log.info("Login thành công");
        return new DashboardPage(driver);
    }
    public boolean isRememberMeChecked() {
        WebElement checkbox = driver.findElement(rememberMeCheckbox);
        return checkbox.isSelected();
    }
    public void clickRememberMe() {
        log.info("Click Remember Me checkbox");
        try {
            click(rememberMeLabel);
        } catch (Exception e) {
            jsClickL(rememberMeCheckbox);
        }
    }
    public ForgotPasswordPage clickForgotPassword() {
        log.info("Click Forgot Password link");
        click(forgotPasswordLink);
        return new ForgotPasswordPage(driver);
    }
    public void clearEmailField() {
        log.info("Clear email");
        clearField(emailInput);
    }
    public void clearPasswordField() {
        log.info("Clear password");
        clearField(passwordInput);
    }
    public String getErrorMessage() {
        if (isAlertDangerDisplayed()) {
            return getText(alertDanger);
        }
        return "";
    }
    // validation from browser
    public String getEmailValidationMessage() {
        WebElement element = driver.findElement(emailInput);
        return (String) executeScript("return arguments[0].validationMessage;", element);
    }
    public String getPasswordValidationMessage() {
        WebElement element = driver.findElement(passwordInput);
        return (String) executeScript("return arguments[0].validationMessage;", element);
    }

    public String getPageHeadingText() {
        return getText(pageHeading);
    }
    public String getPasswordFieldType() {
        return getAttribute(passwordInput, "type");
    }
    public String getEmailFieldType() {
        return getAttribute(emailInput, "type");
    }
    public String getCurrentPageUrl() {
        return getCurrentUrl();
    }
}
