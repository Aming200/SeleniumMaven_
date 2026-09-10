package com.hrm.tests;

import com.hrm.base.BaseTest;
import com.hrm.config.ConfigReader;
import com.hrm.pages.CustomersPage;
import com.hrm.pages.DashboardPage;
import com.hrm.pages.LoginPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
@Feature("Customers")
public class CustomerTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CustomersPage customersPage;

    private String validEmail = ConfigReader.getInstance().getEmail();
    private String validPassword = ConfigReader.getInstance().getPassword();
    private String addCompany = ConfigReader.getInstance().addCompany();
    private String addPhone = ConfigReader.getInstance().addPhone();
    private String addGroupSearch = ConfigReader.getInstance().addGroupSearch();
    private String addAddress = ConfigReader.getInstance().addAddress();
    private String addCity = ConfigReader.getInstance().addCity();
    private String customerSearch = ConfigReader.getInstance().customerSearch();
    private String modifyAddress = ConfigReader.getInstance().modifyAddress();
    private String deletecustomer = ConfigReader.getInstance().deletecustomer();

    long timestamp = System.currentTimeMillis();
    private String addCompanyName = addCompany+timestamp;


    @BeforeMethod()
    @Override
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver);
        dashboardPage = loginPage.login(validEmail,validPassword);
        customersPage=dashboardPage.openCustomers();
    }
    @Test(priority = 0, description = "Verify trang Customers hiển thị đầy đủ các phần tử giao diện")
    @Story("Customers page display")
    public void verifyCustomersPageUI() {
        log.info("Verify trang Customers hiển thị đầy đủ các phần tử giao diện");
        Assert.assertTrue(customersPage.isCustomerLoaded(), "Customers page chưa load đươc");
        Assert.assertTrue(customersPage.getPageTitleText().equals("Customers"), "sai title");
        Assert.assertTrue(customersPage.getCustomerHeading().contains("Customers"), "sai heading text");
        Assert.assertTrue(customersPage.isAddNewCustomerDisplayed(),"Button Add New chưa hiện");
        Assert.assertTrue(customersPage.isInputSearchDisplayed(),"Input Search chưa hiện");
        log.info("PASS");
    }

    @Test(priority = 1, description = "Verify Add New Customer Success")
    @Story("Add New Customer")
    public void verifyAddCustomerSuccess() throws InterruptedException {
        log.info("Verify Add New Customer Success");
        customersPage.addCustomerForm(addCompanyName, addPhone,  addGroupSearch, addAddress, addCity);
        customersPage.verifyAddCustomerSuccess(addCompanyName);
        Assert.assertTrue(customersPage.isinSearch(addCompanyName), "text không có trong bảng");
        log.info("PASS");
    }
    @Test(priority = 2, description = "Verify Modify form Add New Customer Success")
    @Story("Modify Customer")
    public void verifyModifyAddCustomerSuccess() throws InterruptedException {
        log.info("Verify Modify form Add New Customer Success");
        customersPage.modifyCustomer(customerSearch, modifyAddress);
        log.info("PASS");
    }
    @Test(priority = 3, description = "Verify Delete Customer Success")
    @Story("Delete Customer")
    public void verifyDeleteCustomerSuccess() throws InterruptedException {
        log.info("Verify Delete Customer Success");
        customersPage.deleteCustomer(deletecustomer);
        log.info("PASS");
    }
}
