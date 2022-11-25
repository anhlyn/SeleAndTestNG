import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class SampleXpath {

    private WebDriver driver;

    @BeforeClass
    public void openBrowser(){
        System.out.println("Exec TC_onChrome.");
        System.setProperty("webdriver.chrome.driver","//Users//nghitran//Linh//SeleAndTestNG//BrowserDrivers//chromedriver");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
    }

    @Test(enabled = true)
    public void TC01_RegisterEmptyData(){
        driver.get("https://alada.vn/tai-khoan/dang-ky.html");
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[@type='submit']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//label[@id='txtFirstname-error']")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtEmail-error']")).getText(), "Vui lòng nhập email");
        Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCEmail-error']")).getAttribute("for"), "txtCEmail");
        Assert.assertTrue(driver.findElement(By.xpath("//label[@id='txtPassword-error']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//label[@id='txtCPassword-error']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//label[@id='txtPhone-error']")).isDisplayed());
    }

    @AfterClass
    public void closeBrowser(){
        driver.quit();
    }

}
