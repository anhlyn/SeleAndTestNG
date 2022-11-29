import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class Automationfc {
    WebDriver driver = null;
    String URL = "https://automationfc.github.io/basic-form/index.html";

    @BeforeClass
    public void openBrowser(){
        System.setProperty("webdriver.chrome.driver","//Users//nghitran//Linh//SeleAndTestNG//BrowserDrivers//chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void TC01(){
        //Step 1:
        driver.navigate().to(this.URL);
        //Step 2:
        List<WebElement> emails = driver.findElements(By.xpath("//input[@type='email']"));
        for(int i=0; i< emails.size(); i++){
            Assert.assertTrue(emails.get(i).isDisplayed());
            System.out.println("-- " + emails.get(i).getAttribute("id") + " is displayed");
            emails.get(i).sendKeys("Automation Testing");
        }

        WebElement rdoUnder18 = driver.findElement(By.xpath("//input[@type='radio' and @value='under_18']"));
        Assert.assertTrue(rdoUnder18.isDisplayed());
        System.out.println("-- " + rdoUnder18.getAttribute("id") + " is displayed");
        rdoUnder18.click();

        WebElement txtEducation = driver.findElement(By.xpath("//textarea[@id='edu']"));
        Assert.assertEquals(txtEducation.isDisplayed(), true);
        System.out.println("-- " + txtEducation.getAttribute("id") + " is displayed");
        txtEducation.sendKeys("Education");

        //Step 3:
        WebElement figcaptionUser5 = driver.findElement(By.xpath("//div[@class='figcaption']/h5[text()='Name: User5']"));
        Assert.assertEquals(figcaptionUser5.isDisplayed(), false);
        System.out.println("-- Name: User5" + " is not displayed");
    }

    @AfterClass
    public void closeBrowser(){
        driver.quit();
    }
}
