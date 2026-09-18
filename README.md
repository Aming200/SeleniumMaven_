# SeleniumMaven_
Selenium WebDriver + Java + TestNG automation framework with POM, Allure reporting. Demo project testing Perfex CRM login module.

Công nghệ sử dụng: 
Java 11
Selenium WebDriver 4.43.0
TestNG 7.10.2 
WebDriverManager 6.1.0
Log4J Core & API 2.26.1 
Allure TestNG 2.29.1 
AspectJWeaver 1.9.25.1 
Maven Surefire Plugin 3.5.2 

Cài đặt và chạy dự án
1. Yêu cầu hệ thống
JDK 11 trở lên đã được cài đặt và thiết lập biến môi trường `JAVA_HOME`.
Maven đã được cài đặt và thiết lập biến môi trường `MAVEN_HOME`.
(Tùy chọn) Cài đặt Allure Commandline nếu bạn muốn xem báo cáo trên máy tính.

2. Cài đặt
Clone dự án về máy và tải các thư viện (dependencies) qua Maven:

Clone dự án 
git clone <repository_url>

Di chuyển vào thư mục dự án
cd <tên_thư_mục_dự_án>

Cài đặt các thư viện cần thiết
mvn clean install -DskipTests

3. Chạy test
Cách 1: Chạy qua Maven Command Line
Chạy toàn bộ test
mvn clean test
Chạy theo file suite testrun.xml 
mvn clean test -Dsurefire.suiteXmlFiles=src/test/java/com/hrm/tests/testrun.xml

Cách 2: Chạy trực tiếp trên IDE (IntelliJ IDEA / Eclipse)
Mở file `testrun.xml` trong thư mục `src/test/resources/`.
Click chuột phải và chọn Run 'testrun.xml' (hoặc Run '...Suite').

4. Xem báo cáo kết quả (Allure Report)
Sau khi test chạy xong, để xem báo cáo test chi tiết, hãy chạy lệnh sau:
allure serve allure-results
Trình duyệt mặc định sẽ tự động mở và hiển thị báo cáo Allure Report với thông tin chi tiết về từng test case, log và hình ảnh đính kèm (khi test fail)
