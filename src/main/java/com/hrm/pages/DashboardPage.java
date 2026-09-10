package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class DashboardPage extends BasePage {

    private By dashboardHeading = By.xpath("//*[@id=\"wrapper\"]/div[2]");
    private By profileDropdown = By.xpath("//*[@id=\"header\"]/nav/div/ul/li[3]/a");
    private By logoutLink = By.linkText("Logout");
    private By customers_ = By.linkText("Customers");
    private By projects_ = By.linkText("Projects");
    private By tasks_ = By.linkText("Tasks");


    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDashboardLoaded() {
        try {
            waitForUrlContains("/admin");
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public String getDashboardHeading(){
        return getText(dashboardHeading);
    }
    public String getCurrentPageUrl(){
        return getCurrentUrl();
    }
    public String getPageTitleText(){
        return getPageTitle();
    }
    public  boolean isProfileDropdownDisplayed(){
        return isDisplayed(profileDropdown);
    }

    public void openDropdownProfile(){
        log.info("Mở profile dropdown");
        try {
            click(profileDropdown);
        }catch (Exception e){
            //Click JS
            log.warn("Click vào profile dropdown bị lỗi, thử JS click: {}", e.getMessage());
            jsClickL(profileDropdown);
        }
    }
    public void clickLogout() {
        try {
            openDropdownProfile();
            waitForVisible(logoutLink);
            click(logoutLink);
            log.info("Click Logout");
        }catch (Exception e){
            log.warn("Click vào Logout bị lỗi, thử JS click: {}", e.getMessage());
            jsClickL(logoutLink);
        }
    }
    public CustomersPage openCustomers() {
        log.info("Click Customers");
        click(customers_);
        return new CustomersPage(driver);
    }
    public ProjectsPage openProjects() {
        log.info("Click Projects");
        click(projects_);
        return new ProjectsPage(driver);
    }
    public TasksPage openTasks() {
        log.info("Click Tasks");
        click(tasks_);
        return new TasksPage(driver);
    }
    }
