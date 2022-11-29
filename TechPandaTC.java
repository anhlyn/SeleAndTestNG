import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Random;


public class TechPandaTC {

    WebDriver driver;
    String URL = "http://live.techpanda.org/";
    String firstName = "first";
    String lastName = "last";
    String password = "123456";
    String email = "test" + (new Random()).nextInt(999) + "@gmail.com";

    @BeforeClass(alwaysRun = true)
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
    public void Browser_TC01_VerifyUrl(){
        driver.get(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");

        driver.findElement(By.xpath("//a[string()='Create an Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");
    }

    @Test
    public void Browser_TC02_VerifyTitle(){
        driver.get(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertEquals(driver.getTitle(), "Customer Login");

        driver.findElement(By.xpath("//a[string()='Create an Account']")).click();
        Assert.assertEquals(driver.getTitle(), "Create New Customer Account");
    }

    @Test
    public void Browser_TC03_NavigateFunc(){
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
    public void Browser_TC04_GetPageSource(){
        driver.navigate().to(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));

        driver.findElement(By.xpath("//a[string()='Create an Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
    }

    @Test
    public void Login_TC01_EmptyData(){
        driver.get(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));

        driver.findElement(By.xpath("//button[string()='Login']")).click();
        Assert.assertEquals(driver.findElements(By.xpath("//ul[@class='form-list']//div[@class='validation-advice']")).size(), 2);
    }

    @Test
    public void Login_TC02_InvalidEmail(){
        driver.get(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));

        driver.findElement(By.xpath("//ul[@class='form-list']//input[@type='email']")).sendKeys("123434234@12312.123123");
        driver.findElement(By.xpath("//ul[@class='form-list']//input[@type='password']")).sendKeys("123456");

        driver.findElement(By.xpath("//button[string()='Login']")).click();
        Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");
    }

    @Test
    public void Login_TC03_InvalidPassword(){
        driver.get(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));

        driver.findElement(By.xpath("//ul[@class='form-list']//input[@type='email']")).sendKeys("automation@gmail.com");
        driver.findElement(By.xpath("//ul[@class='form-list']//input[@type='password']")).sendKeys("123");

        driver.findElement(By.xpath("//button[string()='Login']")).click();
        Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
    }

    @Test
    public void Login_TC04_IncorrectPassword(){
        driver.get(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));

        driver.findElement(By.xpath("//ul[@class='form-list']//input[@type='email']")).sendKeys("automation@gmail.com");
        driver.findElement(By.xpath("//ul[@class='form-list']//input[@type='password']")).sendKeys("123123123");

        driver.findElement(By.xpath("//button[string()='Login']")).click();
        System.out.println(driver.findElement(By.xpath("//li[@class='error-msg']")).getText());
    }

    @Test(enabled = true)
    public void Login_TC05_CreateNewAccount(){
        driver.get(URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));

        driver.findElement(By.xpath("//a[string()='Create an Account']")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");

        driver.findElement(By.id("firstname")).sendKeys(firstName);
        driver.findElement(By.id("lastname")).sendKeys(lastName);
        driver.findElement(By.id("email_address")).sendKeys(email);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("confirmation")).sendKeys(password);

        driver.findElement(By.xpath("//button[string()='Register']")).click();
        Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/index/");
        Assert.assertTrue(driver.findElement(By.xpath("//li[@class='success-msg']//li[string()='Thank you for registering with Main Website Store.']")).isDisplayed());
        String boxAccount = driver.findElement(By.xpath("//div[@class='col-1']//div[@class='box-content']")).getText();
        Assert.assertFalse(boxAccount.contains(firstName) && boxAccount.contains(lastName) && boxAccount.contains(email));

        //Step 08: Logout system
        driver.findElement(By.xpath("//div[@class='account-cart-wrapper']//a[contains(@href, '/customer/account/')]")).click();
        WebElement logOutLink = driver.findElement(By.xpath("//div[@id='header-account']//a[text()='Log Out']"));
        if(logOutLink.isDisplayed()){
            logOutLink.click();
            Assert.assertTrue(driver.findElement(By.xpath("//p[@class='welcome-msg' and text()='Default welcome msg! ']")).isDisplayed());
        }
    }

    @Test(dependsOnMethods = "Login_TC05_CreateNewAccount", enabled = true)
    public void Login_TC06_ValidAccount(){
        driver.navigate().to(this.URL);
        driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));

        driver.findElement(By.xpath("//ul[@class='form-list']//input[@type='email']")).sendKeys(this.email);
        driver.findElement(By.xpath("//ul[@class='form-list']//input[@type='password']")).sendKeys(this.password);

        driver.findElement(By.xpath("//button[string()='Login']")).click();
        String boxAccount = driver.findElement(By.xpath("//div[@class='col-1']//div[@class='box-content']")).getText();
        Assert.assertTrue(boxAccount.contains(firstName) && boxAccount.contains(lastName) && boxAccount.contains(email));
    }

    @AfterClass(enabled = true)
    public void closeBrowser(){
        driver.quit();
    }

}
