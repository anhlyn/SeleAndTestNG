import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class RegisterOnAladaPage {

    WebDriver driver;
    static String URL = "https://alada.vn/tai-khoan/dang-ky.html";

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

    @Test(groups = "account")
    public void TC01_RegisterEmptyData(){
        driver.get(RegisterOnAladaPage.URL);
        driver.findElement(By.xpath("//form[@id='frmLogin']//button[@type='submit']")).click();

        Assert.assertTrue(driver.findElement(By.xpath("//label[@id='txtFirstname-error']")).isDisplayed());
        Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtEmail-error']")).getText(), "Vui lòng nhập email");
        Assert.assertEquals(driver.findElement(By.xpath("//label[@id='txtCEmail-error']")).getAttribute("for"), "txtCEmail");
        Assert.assertTrue(driver.findElement(By.xpath("//label[@id='txtPassword-error']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//label[@id='txtCPassword-error']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//label[@id='txtPhone-error']")).isDisplayed());
    }
    @Test(groups = "account")
    public void TC02_RegisterInvalidEmail(){
        driver.navigate().to(RegisterOnAladaPage.URL);

        String email = "qctest@123@456";
        driver.findElement(By.xpath("//input[@id='txtFirstname']")).sendKeys("first name");
        driver.findElement(By.xpath("//input[@id='txtEmail']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@id='txtCEmail']")).sendKeys(email);
        driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id='txtCPassword']")).sendKeys("123456");
        driver.findElement(By.xpath("//input[@id='txtPhone']")).sendKeys("0900000000");

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//label[@class='error' and @for='txtEmail']")).getText().trim(), "Vui lòng nhập email hợp lệ");
        Assert.assertEquals(driver.findElement(By.xpath("//label[@class='error' and @for='txtCEmail']")).getText().trim(), "Email nhập lại không đúng");
    }
    @Test(groups = "account")
    public void TCO3_RegisterIncorrectCEmail(){
        driver.navigate().to(RegisterOnAladaPage.URL);

        String email = "qctest@gmail.com";
        String cemail = "qctest1@gmail.com";

        driver.findElement(By.id("txtFirstname")).sendKeys("first name");
        driver.findElement(By.id("txtEmail")).sendKeys(email);
        driver.findElement(By.id("txtCEmail")).sendKeys(cemail);
        driver.findElement(By.id("txtPassword")).sendKeys("123456");
        driver.findElement(By.id("txtCPassword")).sendKeys("123456");
        driver.findElement(By.id("txtPhone")).sendKeys("0900000000");

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//label[@class='error' and @for='txtCEmail']")).getText().trim(), "Email nhập lại không đúng");
    }

    @Test(groups = "account")
    public void TCO4_RegisterWithInvalidPassword(){
        driver.navigate().to(RegisterOnAladaPage.URL);

        driver.findElement(By.id("txtFirstname")).sendKeys("first name");
        driver.findElement(By.id("txtEmail")).sendKeys("qctest@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("qctest@gmail.com");
        driver.findElement(By.id("txtPassword")).sendKeys("12345");
        driver.findElement(By.id("txtCPassword")).sendKeys("12345");
        driver.findElement(By.id("txtPhone")).sendKeys("0900000000");

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//label[@class='error' and @for='txtPassword']")).getText().trim(), "Mật khẩu phải có ít nhất 6 ký tự");
        Assert.assertEquals(driver.findElement(By.xpath("//label[@class='error' and @for='txtCPassword']")).getText().trim(), "Mật khẩu phải có ít nhất 6 ký tự");
    }

    @Test(groups = "account")
    public void TCO5_RegisterWithIncorrectCPassword(){
        driver.navigate().to(RegisterOnAladaPage.URL);

        driver.findElement(By.id("txtFirstname")).sendKeys("first name");
        driver.findElement(By.id("txtEmail")).sendKeys("qctest@gmail.com");
        driver.findElement(By.id("txtCEmail")).sendKeys("qctest@gmail.com");
        driver.findElement(By.id("txtPassword")).sendKeys("123456");
        driver.findElement(By.id("txtCPassword")).sendKeys("654321");
        driver.findElement(By.id("txtPhone")).sendKeys("0900000000");

        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.xpath("//label[@class='error' and @for='txtCPassword']")).getText().trim(), "Mật khẩu bạn nhập không khớp");
    }

    @Test(groups = "account")
    public void TCO6_RegisterWithInvalidPhoneNo(){
        driver.navigate().to(RegisterOnAladaPage.URL);

        String email = "qctest@gmail.com", password = "123456";
        driver.findElement(By.id("txtFirstname")).sendKeys("first name");
        driver.findElement(By.id("txtEmail")).sendKeys(email);
        driver.findElement(By.id("txtCEmail")).sendKeys(email);
        driver.findElement(By.id("txtPassword")).sendKeys(password);
        driver.findElement(By.id("txtCPassword")).sendKeys(password);

        driver.findElement(By.id("txtPhone")).sendKeys("0901");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Số điện thoại phải từ 10-11 số. ".trim());

        driver.findElement(By.id("txtPhone")).clear();
        driver.findElement(By.id("txtPhone")).sendKeys("1234567891");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Assert.assertEquals(driver.findElement(By.id("txtPhone-error")).getText(), "Số điện thoại bắt đầu bằng: 09 - 03 - 012 - 016 - 018 - 019 - 088 - 03 - 05 - 07 - 08");
    }

    @AfterClass(alwaysRun = false)
    public void closeBrowser(){
        driver.quit();
    }

}
