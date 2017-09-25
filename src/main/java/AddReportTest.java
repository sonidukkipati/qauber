import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import testresources.PageResources;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by sc on 5/24/2017.
 */
public class AddReportTest {

    WebDriver dr;
    LoginPage loginPage;
    AddReportSubjectInformationPage addReportSubjectInformationPage;
    Faker faker;

   /* public AddReportDistributionPage getAddReportDistributionPage() {
        return addReportDistributionPage;
    } */

    AddReportDistributionPage addReportDistributionPage;
    NavigationBarPage navigationBarPage;
    AddReportPage addReportPage;
    PageResources pageResources;

  /*  public AddReportSubjectInformationPage getAddReportSubjectInformationPage() {
        return addReportSubjectInformationPage;
    } */


    @BeforeTest
    public void setup() {
        dr = new ChromeDriver();
        loginPage = new LoginPage(dr);
        addReportSubjectInformationPage = new AddReportSubjectInformationPage(dr);
        addReportDistributionPage = new AddReportDistributionPage(dr);
        navigationBarPage = new NavigationBarPage(dr);
        addReportPage = new AddReportPage(dr);
        pageResources = new PageResources(dr);
        faker = new Faker();

    }

    @Test
    public void ReportTest() throws InterruptedException {

        dr.manage().timeouts().implicitlyWait(2000, TimeUnit.SECONDS);
        dr.manage().timeouts().pageLoadTimeout(3000, TimeUnit.SECONDS);
        dr.get("http://qabidder.net/testwave/#/page/login");
        Thread.sleep(5000);
        loginPage.typeUsername();
        Thread.sleep(4000);
        loginPage.typePassword();

        loginPage.loginButtonClick();
        Thread.sleep(3000);

        // Adding the Report
        //Click Add Reports link on Navigation page
        navigationBarPage.AddReportButton.click();

        //select the entity
        addReportPage.entityNamelistByIndex(1).click();

        //addReportPage.selectEntity("Gibson-Gibson");

        addReportPage.setSubjectInformationfield();

        Thread.sleep(3000);
        String lastname= faker.name().lastName();
        String firstname= faker.name().firstName();
        addReportSubjectInformationPage.inputLastname(lastname);
        addReportSubjectInformationPage.inputFirstname(firstname);

        addReportSubjectInformationPage.setDistributionPage();
        Thread.sleep(3000);
        pageResources.getAddReportStopInformation().locationOfStop().click();
        pageResources.getAddReportStopInformation().locationOfStop().sendKeys("san Jose");
        Thread.sleep(3000);
        Thread.sleep(3000);
        pageResources.getAddReportStopInformation().vehiclePage().click();
        Thread.sleep(3000);
        pageResources.getAddReportVehicle().vehicleLicense().sendKeys("ER$%^FGTVBN");
        pageResources.getAddReportVehicle().photosPage().click();
        Thread.sleep(3000);
        pageResources.getAddReportPhotos().previewPage().click();
        Thread.sleep(3000);
        pageResources.getAddReportCommit().reportCommit().click();
        Thread.sleep(5000);
        WebElement el = dr.findElement(By.xpath("//button[text()='YES']"));
        //WebElement el =driver.findElement(By.cssSelector(".btn-success"));
        el.click();
        Thread.sleep(7000);
        dr.findElement(By.xpath("//div[@class='checkbox c-checkbox mt0 mb0 p0']/label")).click();
        Thread.sleep(5000);
        // pageResources.getReports().viewButtonByIndex(0).click();
        Thread.sleep(5000);
        pageResources.getAddReportIDInformation().scrollDown();
        WebElement v1 = dr.findElement(By.xpath("//a[@title='View'][1]"));
        v1.click();
        Thread.sleep(10000);
        pageResources.getAddReportIDInformation().scrollDown();

        String report= dr.findElement(By.tagName("body")).getText();

        Boolean checkStatus= false;

        if (report.contains(lastname))
            checkStatus= true;
        try {
            Assert.assertTrue(checkStatus == true);
            System.out.println("Found " + lastname + " in the View report text");


        } catch (Throwable t) {
            Assert.fail("Could not find " + lastname + " in the View report text");


        }
        if (report.contains(firstname))
            checkStatus= true;
        try {
            Assert.assertTrue(checkStatus == true);
            System.out.println("Found " + firstname + " in the View report text");


        } catch (Throwable t) {
            Assert.fail("Could not find " + firstname + " in the View report text");


        }

    }
 @AfterTest
 public void Cleanup(){
        dr.close();
        dr.quit();
 }


}