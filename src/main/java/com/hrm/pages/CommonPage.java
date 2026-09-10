package com.hrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CommonPage extends BasePage {
    public CommonPage(WebDriver driver) {
        super(driver);
    }
    protected void checkSearchTableInColumn(By input_Search, String value, By table_row, By table_row_detail) throws InterruptedException {
        {
            enterSearchValue(input_Search, value);
            Thread.sleep(1500);
//            waitForPageLoaded();
            waitForVisible(table_row);

            // Đếm số dòng sau search
            int totalRows = driver.findElements(table_row).size();
            System.out.println("Số dòng trả về sau tìm kiếm: " + totalRows);
            for (int i = 0; i < totalRows; i++) {
                List<WebElement> list_row = driver.findElements(table_row);
                WebElement listdetail = list_row.get(i);

                WebElement detail_ = listdetail.findElement(table_row_detail);
                String actualText = detail_.getText().trim();
                System.out.print("Giá trị search dòng " + (i + 1) + ": " + value + " - ");
                System.out.println("Giá trị lấy được từ bảng: " + actualText);

                if (detail_.getText().toUpperCase().contains(value.toUpperCase())) {
                    System.out.println("Pass - Dòng " + (i + 1) + "chứa giá trị tim kiếm ");
                } else {
                    System.out.println("Fail - Dòng  " + (i + 1) + " không chứa giá trị tìm kiếm");
                }

            }
        }
    }
    protected boolean isSearchValueInDropdown(String value, By inputSearch, By resultDropdownDetail, By resultDropdown, By noResultsFound) throws InterruptedException {
        log.info("Nhập thông tin tìm kiếm trong dropdown {}: ",value);
        type(inputSearch, value);
        Thread.sleep(1000);
        try {
            List<WebElement> rows = driver.findElements(resultDropdownDetail);
            if (rows.isEmpty()) {
//            Thread.sleep(1000);
                log.debug("Result Search: {}", driver.findElement(noResultsFound).getText());
                log.info("Result Search: {}", driver.findElement(noResultsFound).getText());
                System.out.println(driver.findElement(noResultsFound).getText());
                return false;
            } else {
                log.debug("Result Search: {}", driver.findElement(resultDropdown).getText());
                log.info("Result Search: {}", driver.findElement(resultDropdown).getText());
                System.out.println("text: " + driver.findElement(resultDropdown).getText());
                return driver.findElement(resultDropdown).getText().toUpperCase().contains(value.toUpperCase());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Lỗi bất thường: " + e.getMessage());
            return false;
        }


    }

    //    public boolean isSearchValueInTable(String value, By inputSearch, By resultTable, By noResultsFound) throws InterruptedException {
//        type(inputSearch, value);
//        Thread.sleep(1000);
//
//        if (isDisplayed(resultTable) == true) {
//            waitForVisible(resultTable);
//            System.out.println("Kêt quả trả về từ bảng sau khi search: " + driver.findElement(resultTable).getText());
//            return driver.findElement(resultTable).getText().toUpperCase().contains(value.toUpperCase());
//        } else {
//            Thread.sleep(1000);
//            System.out.println("Không tìm thấy: " + driver.findElement(noResultsFound).getText());
//            return false;
//        }
//
//    }
    protected void deleteRecordInTable(String nameSearch, By inputSearch, By resultSearch, By resultSearchFirst_, By deleteSuccessMessage) throws InterruptedException {
        while (true) {
            log.info("Nhập thông tin tìm kiếm {} ",nameSearch);
            type(inputSearch, nameSearch);
            Thread.sleep(1000);
            // Kiểm tra xem có bao nhiêu record
            List<WebElement> rows = driver.findElements(resultSearch);
            if (rows.isEmpty()) {
                // Nếu không tìm thấy record nào tức là đã XÓA SẠCH
                log.debug("Đã xóa toàn bộ record có tên: {}", nameSearch);
                System.out.println("Đã xóa toàn bộ record có tên: " + nameSearch);
                break;
            }
            //Nếu có dữ liệu, thực hiện Xóa dòng ĐẦU TIÊN
            jsClickL(resultSearchFirst_);
            //xử lý popup
            acceptAlert();
            log.info("Xóa thành công");
            //Chờ popup thông báo "Xóa thành công" biến mất
            waitForInvisible(deleteSuccessMessage);

        }


    }

    protected boolean isRecordInTable(String value, By inputSearch, By resultTable, By resultRows, By loadingSpinne) throws InterruptedException {
        log.info("Nhập thông tin tìm kiếm {} ",value);
        type(inputSearch, value);
        Thread.sleep(1000);
        try {
            List<WebElement> rows = driver.findElements(resultRows);
            if (rows.isEmpty()) {
                //Không có kết quả
                log.debug("Không tìm thấy kết quả cho: {}", value);
                System.out.println("Không tìm thấy kết quả cho: " + value);
                return false;
            } else {
                //Có kết quả → Lấy text từ BẢNG (element cha), không phải từ List
                String tableText = driver.findElement(resultTable).getText();
                log.debug("Số records: {}", rows.size());
                System.out.println("Số records: " + rows.size());
                log.debug("Nội dung bảng: {}", tableText);
                log.info("Lấy được nội dung bảng: {}",tableText);
                System.out.println("Nội dung bảng: " + tableText);
                return tableText.toUpperCase().contains(value.toUpperCase());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            System.out.println("Lỗi bất thường: " + e.getMessage());
            return false;
        }
    }
    protected boolean isSearchValueInTable(String value, By inputSearch, By resultTable, By noResultsFound, By loadingSpinner) {
        type(inputSearch, value);
        try {
            //Chờ biểu tượng Loading mất đi (Tối đa chờ 10 giây cho an toàn)
            waitForInvisible(loadingSpinner);
            if (isDisplayed(noResultsFound) == true) {
                System.out.println("Không tìm thấy: " + getText(noResultsFound));
                return false;
            } else {
                System.out.println("Số records sau Search: " + getElementCount(resultTable));
                String tableText = getText(resultTable);
                System.out.println("Kết quả trả về từ bảng sau khi search: " + tableText);
                return tableText.toUpperCase().contains(value.toUpperCase());
            }
        } catch (Exception e) {
            //Nếu đợi quá 10 giây mà cái Loading vẫn xoay (do lỗi mạng hoặc server chết)
            System.out.println("Lỗi bất thường: Hệ thống bị treo, mạng đứt hoặc tải quá lâu (> 10s)!");
            return false;
        }
    }
//    public void isSearchValueInTable(String value, By inputSearch, By resultSearch){
//        type(inputSearch,value);
//        waitForVisible(resultSearch);
//        List<WebElement> listselement= driver.findElements(resultSearch);
//        System.out.println(listselement);
//        for (WebElement element: listselement){
//            System.out.println(element);
//            if (element.getText().contains(value)){
//                System.out.println("Tìm thấy giá trị "+ value +" trong bảng ");
//                break;
//            }
//        }
//
//    }

    // doi cho den khi trang load xong
    public void waitForPageLoaded() {
        // wait for jQuery to loaded
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        try {
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(jQueryLoad);
            wait.until(jsLoad);
        } catch (Throwable error) {
//            Assert.fail("Quá thời gian load trang.");
            System.out.println("Quá thời gian load trang.");
        }

    }
}
