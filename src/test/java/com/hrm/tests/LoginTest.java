package com.hrm.tests;

import com.hrm.base.BaseTest;
import com.hrm.config.ConfigReader;
import com.hrm.pages.DashboardPage;
import com.hrm.pages.ForgotPasswordPage;
import com.hrm.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
@Feature("Login")
public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ForgotPasswordPage forgotPasswordPage;
    private String validEmail = ConfigReader.getInstance().getEmail();
    private String validPassword = ConfigReader.getInstance().getPassword();

    @BeforeMethod()
    @Override
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver);
    }

    // Verify UI
    @Test(priority = 0, description = "TC_LOGIN_001-Verify trang Login hiển thị đầy đủ các phần tử giao diện")
    @Story("Login page display")
    @Description("Login page display: Logo, email, password, remember me, login button, forgot password link")
    public void verifyLoginPageUI() {
        log.info("TC_LOGIN_001 Verify trang Login hiển thị đầy đủ các phần tử giao diện");
        Assert.assertTrue(loginPage.isPageLoaded(), "Login page chưa load đươc");
        Assert.assertTrue(loginPage.isLogoDisplayed(), "Logo chưa hiện");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login buton chưa hiện");
        Assert.assertTrue(loginPage.isRememberMeDisplayed(), "Remember me chưa hiện");
        Assert.assertTrue(loginPage.isForgotPasswordDisplayed(), "Forgot Password link chưa hiện");
        log.info("TC_LOGIN_001-PASS");
    }

    @Test(priority = 1, description = "TC_LOGIN_002-Verify email khi không có kí tự @")
    @Story("Validation Email")
    @Description("Nhập email không có kí tự @")
    public void verifyEmailNotSign() {
        log.info("TC_LOGIN_002 Verify email khi không có kí tự @");
        loginPage.enterEmail("invalidemail");
        loginPage.enterPassword("123456");
        loginPage.clickLogin();
        String message = loginPage.getEmailValidationMessage();
        log.info("TC_LOGIN_002 Message: {}",message);
        Assert.assertTrue(message.contains("@"), "message nên đề câp đến kí tự @");
        log.info("TC_LOGIN_002-PASS");

    }

    @Test(priority = 2, description = "TC_LOGIN_003-Verify email khi có @ thiếu domain")
    @Story("Validation Email")
    @Description("Nhâp email có kí tự @, thiếu domain")
    public void verifyEmailMissDomain() {
        log.info("TC_LOGIN_003 Verify email khi có @ thiếu domain");
        loginPage.enterEmail("invalidemail@");
        loginPage.enterPassword("123456");
        loginPage.clickLogin();
        String message = loginPage.getEmailValidationMessage();
        log.info("TC_LOGIN_003 Message: {}",message);
        Assert.assertFalse(message.isEmpty(), "message nên hiển thị khi email không có domain");
        log.info("TC_LOGIN_003-PASS");

    }

    @Test(priority = 3, description = "TC_LOGIN_004-Verify password hiển thị dưới dang masked")
    @Story("Validation password")
    @Description("nhập password, kiểm tra type='password'")
    public void verifyPasswordFieldMasked() {
        log.info("TC_LOGIN_004 Verify password hiển thị dưới dang masked");

        loginPage.enterPassword("123456");

        String typePass = loginPage.getPasswordFieldType();
        log.info("TC_LOGIN_004 Password type: {}", typePass);
        Assert.assertEquals(typePass, "password", "Password nên có type='password' để ẩn kí tự");
        log.info("TC_LOGIN_004-PASS");

    }

    @Test(priority = 4, description = "TC_LOGIN_005-Verify checkbox Remember me mặc đinh ở trạng thái chưa check")
    @Story("Remember me")
    @Description("Remember me, default: uncheck")
    public void verifyRememberMeUncheck() {
        log.info("TC_LOGIN_005 Verify checkbox Remember me mặc đinh ở trạng thái chưa check");
        log.info("[TC_LOGIN_005] Remember Me checked: {}", loginPage.isRememberMeChecked());
        Assert.assertFalse(loginPage.isRememberMeChecked(), "checkbox Remember me mặc đinh nên ở trạng thái chưa check");
        log.info("TC_LOGIN_005-PASS");

    }

    // Authentication
    @Test(priority = 5, description = "TC_LOGIN_006-Verify đăng nhập thành công với email và password hơp lệ")
    @Story("Login Success")
    @Description("Login với email và password hơp lệ, chuyển đến dashboard")
    public void verifyLoginSuccess() {
        log.info("TC_LOGIN_006 Verify đăng nhập thành công với email và password hơp lệ");
        dashboardPage = loginPage.login(validEmail, validPassword);
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard chưa load được");
        log.info("[TC_LOGIN_006] Current URL sau login: {}", dashboardPage.getCurrentPageUrl());
        Assert.assertFalse(dashboardPage.getCurrentPageUrl().contains("authentication"), "url không bao gồm 'authentication' khi login thành công");
        log.info("TC_LOGIN_006-PASS");

    }

    @Test(priority = 6, description = "TC_LOGIN_007-Verify hiển thị thông báo lỗi khi để trống email")
    @Story("Validation Empty")
    @Description("để trống email")
    public void verifyMessageEmptyEmail() {
        log.info("TC_LOGIN_007 Verify hiển thị thông báo lỗi khi để trống email");
        loginPage.enterPassword("pass12345");
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isAlertDangerDisplayed(), "message lỗi nên hiên thị khi để trông email");
        log.info("TC_LOGIN_007 Error message: {}", loginPage.getErrorMessage());
        log.info("TC_LOGIN_007-PASS");

    }

    @Test(priority = 7, description = "TC_LOGIN_008-Verify hiển thị thông báo lỗi khi để trống password")
    @Story("Validation Empty")
    @Description("để trống password")
    public void verifyMessageEmptyPassword() {
        log.info("TC_LOGIN_008 Verify hiển thị thông báo lỗi khi để trống password");
        loginPage.enterEmail(validEmail);
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isAlertDangerDisplayed(), "message lỗi nên hiên thị khi để trống password");
        log.info("TC_LOGIN_008 Error message: {}", loginPage.getErrorMessage());
        log.info("TC_LOGIN_008-PASS");

    }

    @Test(priority = 8, description = "TC_LOGIN_009-Verify hiển thị đồng thời cả hai thông báo lỗi khi để trống cả Email và Password")
    @Story("Validation Empty")
    @Description("Để trông cả email và password")
    public void verifyMessageEmptyAllField() {
        log.info("TC_LOGIN_009 Verify hiển thị đồng thời cả hai thông báo lỗi khi để trống cả Email và Password");

        loginPage.clearEmailField();
        loginPage.clearPasswordField();
        loginPage.clickLogin();
        Assert.assertTrue(loginPage.isAlertDangerDisplayed(), "message lỗi nên hiên thị khi để trống cả 2 truờng");
        log.info("TC_LOGIN_009 Error message: {}", loginPage.getErrorMessage());
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("email") || loginPage.getErrorMessage().toLowerCase().contains("password"),
                "Message lỗi nên đề cập đến email hoăc password");
        log.info("TC_LOGIN_009-PASS");

    }

    @Test(priority = 9, description = "TC_LOGIN_010-Verify thông báo 'Invalid email or password' khi Email đúng nhưng Password sai")
    @Story("Invalid password")
    @Description("Nhập email đúng, password sai")
    public void verifyMessageCorectEmailWrongPassword() {
        log.info("TC_LOGIN_010 Verify thông báo 'Invalid email or password' khi Email đúng nhưng Password sai");
        loginPage.login(validEmail, "Wrongpass123");
        Assert.assertTrue(loginPage.isAlertDangerDisplayed(), "message lỗi nên hiên thị khi nhập sai Password");
        log.info("TC_LOGIN_010 Error message: {}",loginPage.getErrorMessage());
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("invalid"), "Message lỗi nên chứa Invalid");
        log.info("TC_LOGIN_010-PASS");

    }

    @Test(priority = 10, description = "TC_LOGIN_011-Verify thông báo 'Invalid email or password' khi Email sai nhưng Password đúng")
    @Story("Invalid email")
    @Description("Nhập email sai, password đúng")
    public void verifyMessageCorectPasswordWrongEmail() {
        log.info("TC_LOGIN_011 Verify thông báo 'Invalid email or password' khi Email sai nhưng Password đúng");
        loginPage.login("Wrongemail@mm.com", validPassword);
        Assert.assertTrue(loginPage.isAlertDangerDisplayed(), "message lỗi nên hiên thị khi nhập sai email");
        log.info("TC_LOGIN_011 Error message: {}",loginPage.getErrorMessage());
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("invalid"), "Message lỗi nên chứa Invalid");
        log.info("TC_LOGIN_011-PASS");

    }

    @Test(priority = 11, description = "TC_LOGIN_012-Verify thông báo lỗi khi cả Email và Password đều sai")
    @Story("Invalid both")
    @Description("Nhập email sai, password sai")
    public void verifyMessageBothWrong() {
        log.info("TC_LOGIN_012 Verify thông báo lỗi khi cả Email và Password đều sai");
        loginPage.login("Wrongemail@mm.com", "Wrongpass123");
        Assert.assertTrue(loginPage.isAlertDangerDisplayed(), "message lỗi nên hiên thị khi nhập sai cả 2 truờng");
        log.info("TC_LOGIN_012 Error message: {}",loginPage.getErrorMessage());
        Assert.assertTrue(loginPage.getErrorMessage().toLowerCase().contains("invalid"), "Message lỗi nên chứa Invalid");
        log.info("TC_LOGIN_012-PASS");

    }

    @Test(priority = 12, description = "TC_LOGIN_013-Verify user đã đăng nhập mà truy cập lại trang Login sẽ redirect về Dashboard")
    @Story("Manage session")
    @Description("Login trước, navigate đến url Login")
    public void verifyRedirectWhenAlreadyLoggedIn() {
        log.info("TC_LOGIN_013 Verify user đã đăng nhập mà truy cập lại trang Login sẽ redirect về Dashboard");
        dashboardPage = loginPage.login(validEmail, validPassword);
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Nên hiển thị Dashboard page sau khi đăng nhâp");
        driver.get("https://crm.anhtester.com/admin/authentication");
        log.info("[TC_LOGIN_013] URL sau navigating đến login khi đã login: {}", dashboardPage.getCurrentPageUrl());
        Assert.assertFalse(dashboardPage.getCurrentPageUrl().contains("authentication"), "Nên redirect ra ngoài trang Login khi đã login");
        log.info("TC_LOGIN_013-PASS");

    }

    @Test(priority = 13, description = "TC_LOGIN_014-Verify click 'Forgot Password?' chuyển đến đúng trang")
    @Story("Forgot Password link")
    @Description("click Forgot Password link")
    public void verifyNavigateForgotPassword() {
        log.info("TC_LOGIN_014 Verify click 'Forgot Password?' chuyển đến đúng trang");
        forgotPasswordPage = loginPage.clickForgotPassword();
        log.info("TC_LOGIN_014 Current Url: {}", forgotPasswordPage.getCurrentPageUrl());
        Assert.assertTrue(forgotPasswordPage.getCurrentPageUrl().contains("forgot_password"), "Nên redirect đến ForgotPassword page");
        Assert.assertTrue(forgotPasswordPage.isPageLoaded(), "Chưa load được ForgotPassword page");

        log.info("TC_LOGIN_014-PASS");

    }

    @Test(priority = 14, description = "TC_LOGIN_015-Verify Logout redirect về trang Login và hủy session")
    @Story("Logout")
    @Description("Login, Logout qua profile dropdown, verify login page")
    public void verifyLogout() {
        log.info("TC_LOGIN_015 Verify Logout redirect về trang Login và hủy session");
        dashboardPage = loginPage.login(validEmail, validPassword);
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Nên hiển thị Dashboard page sau khi đăng nhâp");
        dashboardPage.clickLogout();
        LoginPage loginPageAfterLogout = new LoginPage(driver);
        String currentUrl = loginPageAfterLogout.getCurrentPageUrl();
        log.info("TC_LOGIN_015 URL sau logout: {}", currentUrl);

        Assert.assertTrue(currentUrl.contains("authentication"),
                "Nên redirect đên Login page sau khi logout");

        log.info("TC_LOGIN_015-PASS");

    }

}
