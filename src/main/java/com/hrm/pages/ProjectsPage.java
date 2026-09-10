package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class ProjectsPage extends CommonPage {
    private By projects_ = By.linkText("Projects");
    private By projectsHeading = By.xpath("//div[@class='col-md-12']/h4/span");
    private By addNewProject_ = By.xpath("//div[@class='_buttons tw-mb-2 sm:tw-mb-4']/a[1]");
    private By addNewProjectText_ = By.xpath("//*[@id=\"project_form\"]/div/h4");
    private By projectName_ = By.id("name");
    private By customer_ = By.xpath("//*[@id=\"tab_project\"]/div[2]/div/button");
    private By customerSearch_ = By.xpath("//*[@id=\"tab_project\"]/div[2]/div/div/div[1]/input");
    private By startDate_ = By.id("start_date");
    private By deadline_ = By.id("deadline");
    private By saveBtn_ = By.xpath("//*[@id=\"project_form\"]/div/div/div[2]/button");
    private By inputSearch_ = By.xpath("//*[@id=\"projects_filter\"]/label/div/input");
    private By projectNameInTable_ = By.xpath("//*[@id=\"projects\"]/tbody/tr");
    private By xpath_detail_ = By.xpath("./td[2]");
    private By result_Customer_Search = By.xpath("//*[@id=\"bs-select-6\"]/ul");
    private By customerSearchDetail = By.xpath("//*[@id=\"bs-select-6\"]/ul/li");
    private By result_Customer_Search_Detail = By.xpath("./li");
    private By noResultsFound_ = By.xpath("//*[@id='tab_project']//div[@class='status' and @style='']");
    private By addSuccessMessage_ = By.xpath("//*[@id=\"alert_float_1\"]/span[2]");
    private By successMessageClose_ = By.xpath("//*[@id=\"alert_float_1\"]/button");
    private By processingLocator = By.xpath("//*[@id=\"projects_processing\"]");
    private By resultSearch_ = By.xpath("//*[@id=\"projects\"]/tbody");
    private By resultSearchToDelete_ = By.xpath("//tr[contains(@class, 'has-row-options')]");
    private By deleteBtn_ = By.xpath("//*[@id=\"projects\"]/tbody/tr[1]/td[2]/div/a[4]");
    private By newCreatedProjectName = By.xpath("//*[@id=\"project_view_name\"]/div/div/button/div/div/div");
    private By deleteSuccessMessage = By.xpath("//*[@id=\"alert_float_1\"]/span[2]");


    public ProjectsPage(WebDriver driver) {
        super(driver);
    }

    public boolean isProjectLoaded() {
        try {
            waitForUrlContains("/projects");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddNewProjectDisplayed() {
        return isDisplayed(addNewProject_);
    }

    public boolean isPageHeadingDisplayed() {
        return isDisplayed(projectsHeading);
    }

    public boolean isInputSearchDisplayed() {
        return isDisplayed(inputSearch_);
    }

    public String getProjectHeading() {
        return getText(projectsHeading);
    }

    public String getCurrentPageUrl() {

        return getCurrentUrl();
    }

    public String getPageTitleText() {

        return getPageTitle();
    }

    public void addProjectForm(String projectName, String inputCustomer, String startDate, String deadline) throws InterruptedException {
        if (getPageTitle().contains("Projects")) {
            System.out.println("Đang ở Projects Summary");
        } else {
            click(projects_);
        }
        log.info("Mở Form Add New Project");
        click(addNewProject_);
        log.info("Enter projectName: {}", projectName);
        type(projectName_, projectName);
        click(customer_);
        Assert.assertTrue(isCustomerVisibleInProjectForm(inputCustomer), "text không có trong Dropdown");
        log.info("Select customer trong dropdown: {}", inputCustomer);
        selectDynamicDropdown(result_Customer_Search, result_Customer_Search_Detail, inputCustomer);
        log.info("Enter startDate: {}", startDate);
        scrollToElement(startDate_);
        type(startDate_, startDate);
        log.info("Enter deadline: {}", deadline);
        scrollToElement(deadline_);
        type(deadline_, deadline);
        log.info("Click Save");
        click(saveBtn_);
        addProjectSuccess();
    }
    public boolean isCustomerVisibleInProjectForm(String customerName) throws InterruptedException {
//        if (getPageTitle().contains("Add new project")) {
//            System.out.println("Đang ở form addNew");
//        } else {
//            click(addNewProject_);
//        }
//        click(customer_);
        return isSearchValueInDropdown(customerName, customerSearch_, customerSearchDetail, result_Customer_Search, noResultsFound_);
    }

    public void addProjectSuccess() {
        try {
            waitForVisible(addSuccessMessage_);
            log.info("Thêm mới thành công: {}", getText(addSuccessMessage_));
//            System.out.println("Thêm mới thành công: " + getText(addSuccessMessage_));
            click(successMessageClose_);
        } catch (Exception e) {
            log.info("Thêm mới không thành công hoặc thông báo chưa kịp xuất hiện!");
//            System.out.println("Thêm mới không thành công hoặc thông báo chưa kịp xuất hiện!");
        }
    }

    public void verifyAddProjectSuccess(String value) {
        System.out.println(getPageTitle());
        Assert.assertTrue(getPageTitle().equals(value), "sai title");
        System.out.println(getText(newCreatedProjectName));
        Assert.assertTrue(getText(newCreatedProjectName).contains(value), "sai text");
    }

    public boolean isinSearchTable(String value) throws InterruptedException {
        if (getPageTitle().contains("Projects")) {
            System.out.println("Đang ở Projects Summary");
        } else {
            click(projects_);
        }
        return isRecordInTable(value, inputSearch_, resultSearch_, resultSearchToDelete_, processingLocator);
//        return isSearchValueInTable(value,inputSearchCustomer_,resultSearch_,noResultSearch_,processingLocator);
    }

    public void deleteProject(String nameSearch) throws InterruptedException {
        if (getPageTitle().contains("Projects")) {
            System.out.println("Đang ở Projects Summary");
        } else {
            click(projects_);
        }
        deleteRecordInTable(nameSearch, inputSearch_, resultSearchToDelete_, deleteBtn_, deleteSuccessMessage);
        log.info("Đã xóa toàn bộ project có tên: {}", nameSearch);
    }
}
