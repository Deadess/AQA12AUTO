package lesson3;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {
    WebDriver driver;
    public WebDriverWait wait;
    String baseUrl = "https://qa-course-01.andersenlab.com/";
    By emailLocator = By.name("email");
    By passwordLocator = By.name("password");
    By loginButtonLocator = By.xpath("//button[@type='submit']");
    By signOutLocator = By.cssSelector("[alt=\"Sign Out\"]");
    WebElement loginField;
    WebElement passwordField;
    WebElement loginButton;
    WebElement signOutElement;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public void login(String login, String password) {
        driver.get(baseUrl);
        loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailLocator));
        loginField.sendKeys(login);
        passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator));
        passwordField.sendKeys(password);
        loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
        loginButton.click();
    }
    @DataProvider(name = "loginData")
    public Object[][] provideLoginData() {
        return new Object[][]{
                {"bb@bb", "123123123"},
                {"cc@cc", "123123123"},
                {"dd@dd", "321321321"}
        };
    }
    @Test(dataProvider = "loginData", groups = "dataProviderTests")
    public void testValidLogin(String username, String password) {
        setUp();
        login(username, password);
        signOutElement = wait.until(ExpectedConditions.visibilityOfElementLocated(signOutLocator));
        Assert.assertTrue(signOutElement.isDisplayed());
        tearDown();
    }
    @After
    public void tearDown() {
        driver.quit();
    }
}
