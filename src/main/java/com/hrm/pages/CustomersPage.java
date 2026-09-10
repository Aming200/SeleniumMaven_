package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.Alert;

import java.util.List;

public class CustomersPage extends CommonPage {

    private By customers_ = By.linkText("Customers");
    private By customersHeading = By.xpath("//div[@class='mbot15']/h4/span");
    private By addNewCustomer = By.xpath("//div[@class='_buttons']/a[1]");
    private By company_ = By.id("company");
    private By phone_ = By.id("phonenumber");
    private By addNewGroup = By.xpath("//div[@class='input-group-btn']/a");
    private By inputGroup = By.id("name");
    private By saveGroupBtn = By.xpath("//*[@id=\"customer-group-modal\"]/div[2]/button[2]");
    private By closeGroupBtn = By.xpath("//*[@id=\"customer-group-modal\"]/div[2]/button[1]");
    private By groupDropdown = By.xpath("//button[@class='btn dropdown-toggle btn-default bs-placeholder']");
    private By inputSearchGroup = By.xpath("//div[@class='input-group input-group-select select-groups_in[]']/div/div/div[1]/input");
    private By selectAll_ = By.xpath("//button[@class='actions-btn bs-select-all btn btn-default']");
    private By deselectAll_ = By.xpath("//button[@class='actions-btn bs-deselect-all btn btn-default']");
    private By address_ = By.id("address");
    private By city_ = By.id("city");
    private By saveBtn_ = By.xpath("//*[@id=\"profile-save-section\"]/button[2]");
    private By saveBtnModify_ = By.xpath("//*[@id=\"profile-save-section\"]/button");
    private By inputSearchCustomer_ = By.xpath("//*[@id=\"clients_filter\"]/label/div/input");
    private By resultSearch_ = By.xpath("//*[@id=\"clients\"]/tbody");
    private By resultSearchToDelete_ = By.xpath("//tr[contains(@class, 'has-row-options')]");
    private By resultSearchFirst_ = By.xpath("//*[@id=\"clients\"]/tbody/tr[1]/td[3]/div/a[3]");
    private By modifyRecord_ = By.xpath("//*[@id=\"clients\"]/tbody/tr/td[3]/a");
    private By noResultSearch_ = By.xpath("//tr[(@class='odd')]");
    private By detailResult_ = By.xpath("./td[3]/a");
    private By detailDeleteBtn_ = By.xpath("./td[3]/div/a[3]");
    private By addSuccessMessage_ = By.xpath("//*[@id=\"alert_float_1\"]/span[2]");
    private By successMessageClose_ = By.xpath("//*[@id=\"alert_float_1\"]/button");
    private By modifySuccessMessage_ = By.xpath("//*[@id=\"alert_float_1\"]/span[2]");
    private By modifyMessageClose_ = By.xpath("//*[@id=\"alert_float_1\"]/button");
    private By processingLocator = By.xpath("//*[@id=\"clients_processing\"]");
    private By resultSearchTest_ = By.xpath("//*[@id=\'clients\']/tbody");
    private By newCreatedCustomerName = By.xpath("//*[@id=\"wrapper\"]/div/div/div[1]/h4/div/span");
    private By deleteSuccessMessage = By.xpath("//*[@id=\"alert_float_1\"]/span[2]");


    public CustomersPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCustomerLoaded() {
        try {
            waitForUrlContains("/clients");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAddNewCustomerDisplayed() {
        return isDisplayed(addNewCustomer);
    }

    public boolean isPageHeadingDisplayed() {
        return isDisplayed(customersHeading);
    }

    public boolean isInputSearchDisplayed() {
        return isDisplayed(inputSearchCustomer_);
    }

    public String getCustomerHeading() {
        return getText(customersHeading);
    }

    public String getCurrentPageUrl() {

        return getCurrentUrl();
    }

    public String getPageTitleText() {

        return getPageTitle();
    }

    public void addCustomerForm(String company, String phone, String groupSearch, String address, String city) {
        if (getPageTitle().contains("Customers")) {
            System.out.println("Đang ở Customers Summary");
        } else {
            click(customers_);
        }
        log.info("Mở Form Add New Customer");
        click(addNewCustomer);
        log.info("Enter company: {}", company);
        type(company_, company);
        log.info("Enter phone: {}", phone);
        type(phone_, phone);
//        click(addNewGroup);
//        type(inputGroup, inputAddGroup);
//        click(saveGroupBtn);
        log.info("Select group: {}", groupSearch);
        click(groupDropdown);
        type(inputSearchGroup, groupSearch);
        click(selectAll_);
        click(groupDropdown);
        log.info("Enter address: {}", address);
        scrollToElement(address_);
        type(address_, address);
        log.info("Enter city: {}", city);
        scrollToElement(city_);
        type(city_, city);
        log.info("Click Save");
        scrollToElement(saveBtn_);
        click(saveBtn_);
        addCustomerSuccess();
    }

    public void addCustomerSuccess() {
        try {
            waitForVisible(addSuccessMessage_);
//            System.out.println("Thêm mới thành công: " + getText(addSuccessMessage_));
            log.info("Thêm mới thành công: {}", getText(addSuccessMessage_));
            click(successMessageClose_);
        } catch (Exception e) {
            log.info("Thêm mới không thành công hoặc thông báo chưa kịp xuất hiện!");
//            System.out.println("Thêm mới không thành công hoặc thông báo chưa kịp xuất hiện!");
        }
    }

    public void modifyCustomerSuccess() {
        try {
            waitForVisible(modifySuccessMessage_);
//            System.out.println("Sửa thành công: " + getText(modifySuccessMessage_));
            log.info("Sửa thành công: {}", getText(modifySuccessMessage_));
            click(modifyMessageClose_);
        } catch (Exception e) {
            log.info("Sửa không thành công hoặc thông báo chưa kịp xuất hiện!");
//            System.out.println("Sửa không thành công hoặc thông báo chưa kịp xuất hiện!");
        }
    }

    public void verifyAddCustomerSuccess(String value) {
        System.out.println(getPageTitle());
        Assert.assertTrue(getPageTitle().equals(value), "sai title");
        System.out.println(getText(newCreatedCustomerName));
        Assert.assertTrue(getText(newCreatedCustomerName).contains(value), "sai text");
    }

    public void searchCustomerInTable(String value) throws InterruptedException {
        if (isDisplayed(customersHeading) == true) {
            System.out.println("đang ở Customers Summary");
        } else {
            click(customers_);
        }
        checkSearchTableInColumn(inputSearchCustomer_, value, resultSearch_, detailResult_);
//        enterSearchValue(inputSearchCustomer_, value);
//        Thread.sleep(3000);
//        int total = driver.findElements(resultSearch_).size();
//        System.out.println("số dong sau tim kiêm: "+total);
//        for (int i = 1; i <= total; i++) {
//            WebElement driverElement= driver.findElement(By.xpath("//*[@id=\'clients\']/tbody/tr["+i+"]/td[3]/a[1]"));
//            if (driverElement.getText().toUpperCase().contains(value.toUpperCase())) {
//                System.out.println("Pass - Dòng " + i  + "chứa giá trị tim kiếm ");
//            } else {
//                System.out.println("Fail - Dòng  " + i + " không chứa giá trị tìm kiếm");
//            }
//
//        }
    }

    public boolean isinSearch(String value) throws InterruptedException {
        if (getPageTitle().contains("Customers")) {
            System.out.println("Đang ở Customers Summary");
        } else {
            click(customers_);
        }
        return isRecordInTable(value, inputSearchCustomer_, resultSearch_, resultSearchToDelete_, processingLocator);
//        return isSearchValueInTable(value,inputSearchCustomer_,resultSearch_,noResultSearch_,processingLocator);
    }

    public void deleteCustomer(String nameSearch) throws InterruptedException {
        if (getPageTitle().contains("Customers")) {
            System.out.println("Đang ở Customers Summary");
        } else {
            click(customers_);
        }
        deleteRecordInTable(nameSearch, inputSearchCustomer_, resultSearchToDelete_, resultSearchFirst_, deleteSuccessMessage);
        log.info("Đã xóa toàn bộ khách hàng có tên: {}", nameSearch);
    }

    public void modifyCustomer(String nameSearch, String address) throws InterruptedException {
        if (getPageTitle().contains("Customers")) {
            System.out.println("Đang ở Customers Summary");
        } else {
            click(customers_);
        }
        log.info("Tìm kiếm khách hang có tên: {} ", nameSearch);
        type(inputSearchCustomer_, nameSearch);
        //Chờ bảng kết quả load xong
        Thread.sleep(1000);
//        waitForInvisible(processingLocator);
        // Kiểm tra xem có bao nhiêu record
        List<WebElement> rows = driver.findElements(resultSearchToDelete_);
        if (rows.isEmpty()) {
            log.info("Không tim thấy khách hang có tên: {} ", nameSearch);
            System.out.println("Không tim thây record để sửa ");
        } else {
            System.out.println(getText(modifyRecord_));
            log.info("Mở form sửa");
            click(modifyRecord_);
            log.info("Sửa address: {} ", address);
            scrollToElement(address_);
            type(address_, address);
            scrollToElement(saveBtnModify_);
            log.info("Click save");
            click(saveBtnModify_);
            modifyCustomerSuccess();
        }

    }


}
