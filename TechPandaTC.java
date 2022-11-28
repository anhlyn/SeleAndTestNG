import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TechPandaTC {

    WebDriver driver;
    String URL = "http://live.techpanda.org/";

    @BeforeClass
    @Parameters("browser")
    public void openBrowser(String browserName){

        System.out.println("<Parameter is " + browserName + "/>");
        if(browserName.contentEquals("chrome")){
            System.setProperty("webdriver.chrome.driver","//Users//nghitran//Linh//SeleAndTestNG//BrowserDrivers//chromedriver");
            driver = new ChromeDriver();
        }else if(browserName.contentEquals("firefox")){
            System.setProperty("webdriver.gecko.driver","//Users//nghitran//Linh//SeleAndTestNG//BrowserDrivers//geckodriver");
            driver = new FirefoxDriver();
        }

        driver.manage().window().maximize();
    }

    @Test
    public void TC01_VerifyUrl(){
        driver.get(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");

        driver.findElement(By.xpath("//a[string()='Create an Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
    }

    @Test
    public void TC02_VerifyTitle(){
        driver.get(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertEquals(driver.getTitle(), "Customer Login");

        driver.findElement(By.xpath("//a[string()='Create an Account']")).click();
        Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
    }

    @Test
    public void TC03_NavigateFunc(){
        driver.get(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        driver.findElement(By.xpath("//a[string()='Create an Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");

        //Step 05: Back to Login page
        driver.navigate().back();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");

        //Step 07: Forward to Register page
        driver.navigate().forward();
        Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
    }

    @Test
    public void TC04_GetPageSource(){
        driver.navigate().to(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));

        driver.findElement(By.xpath("//a[string()='Create an Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
    }

    @AfterClass
    public void closeBrowser(){
        driver.quit();
    }

}
