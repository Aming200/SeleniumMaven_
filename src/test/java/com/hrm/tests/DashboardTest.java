package com.hrm.tests;

import com.hrm.base.BaseTest;
import com.hrm.config.ConfigReader;
import com.hrm.pages.DashboardPage;
import com.hrm.pages.LoginPage;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Dashboard")
public class DashboardTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    private String validEmail = ConfigReader.getInstance().getEmail();
    private String validPassword = ConfigReader.getInstance().getPassword();
    @BeforeMethod()
    @Override
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver);
        dashboardPage = loginPage.login(validEmail,validPassword);
    }
    // Verify UI
    @Test(priority = 0, description = "Verify trang Dashboard hiển thị đầy đủ các phần tử giao diện")
    public void verifyDashboardPageUI() {
        log.info("Verify trang Dashboard hiển thị đầy đủ các phần tử giao diện");
        Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard page chưa load đươc");
        Assert.assertTrue(dashboardPage.isProfileDropdownDisplayed(),"Profile dropown chưa hiện");
        log.info("PASS");
    }

}
