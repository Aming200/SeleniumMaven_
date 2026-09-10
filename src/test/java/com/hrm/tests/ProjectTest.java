package com.hrm.tests;

import com.hrm.base.BaseTest;
import com.hrm.config.ConfigReader;
import com.hrm.pages.DashboardPage;
import com.hrm.pages.LoginPage;
import com.hrm.pages.ProjectsPage;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Projects")
public class ProjectTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ProjectsPage projectsPage;

    private String validEmail = ConfigReader.getInstance().getEmail();
    private String validPassword = ConfigReader.getInstance().getPassword();
    private String addProjectName = ConfigReader.getInstance().addProjectName();
    private String addCompany = ConfigReader.getInstance().addCompany();
    private String addStartDate = ConfigReader.getInstance().addStartDate();
    private String addDeadline = ConfigReader.getInstance().addDeadline();
    private String deleteProjectSearch = ConfigReader.getInstance().deleteProjectSearch();
    long timestamp = System.currentTimeMillis();
    private String addNamePoject=  addProjectName+timestamp;
    @BeforeMethod()
    @Override
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver);
        dashboardPage = loginPage.login(validEmail,validPassword);
        projectsPage=dashboardPage.openProjects();
    }
    @Test(priority = 0, description = "Verify trang Projects hiển thị đầy đủ các phần tử giao diện")
    @Story("Projects page display")
    public void verifyProjectsPageUI() {
        log.info("Verify trang Projects hiển thị đầy đủ các phần tử giao diện");
        Assert.assertTrue(projectsPage.isProjectLoaded(), "Projects page chưa load đươc");
        Assert.assertTrue(projectsPage.getPageTitleText().equals("Projects"), "sai title");
        Assert.assertTrue(projectsPage.getProjectHeading().contains("Projects"), "sai heading text");
        Assert.assertTrue(projectsPage.isAddNewProjectDisplayed(),"Button Add New chưa hiện");
        Assert.assertTrue(projectsPage.isInputSearchDisplayed(),"Input Search chưa hiện");
        log.info("PASS");
    }
    @Test(priority = 1, description = "Verify Add New Project Success")
    @Story("Add New Project")
    public void verifyAddProjectSuccess() throws InterruptedException {
        log.info("Verify Add New Project Success");
        projectsPage.addProjectForm(addNamePoject, addCompany, addStartDate, addDeadline);
        projectsPage.verifyAddProjectSuccess(addNamePoject);
        Assert.assertTrue(projectsPage.isinSearchTable(addNamePoject), "text không có trong bảng");
        log.info("PASS");
    }
    @Test(priority = 2, description = "Verify Delete Project Success")
    @Story("Delete Project")
    public void verifyDeleteProjectSuccess() throws InterruptedException {
        log.info("Verify Delete Project Success");
        projectsPage.deleteProject(deleteProjectSearch);
        log.info("PASS");
    }
}
