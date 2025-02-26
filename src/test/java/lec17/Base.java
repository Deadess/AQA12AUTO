//package lec18;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.jupiter.api.AfterEach;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class Base {
//    WebDriver driver;
//    public WebDriverWait wait;
//    String baseUrl = "https://qa-course-01.andersenlab.com/";
//
//    By emailLocator = By.name("email");
//    By passwordLocator = By.name("password");
//    By loginButtonLocator = By.xpath("//button[@type='submit']");
//
//    WebElement loginField;
//    WebElement passwordField;
//    WebElement loginButton;
//
//    @Before
//    public void setUp() {
//        driver = new ChromeDriver();
//        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//    }
//
//    //login
//    public void login(String login, String password) {
//        driver.get(baseUrl);
//        loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailLocator));
//        loginField.sendKeys(login);
//        passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator));
//        passwordField.sendKeys(password);
//        loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
//        loginButton.click();
//    }
//
//    @After
//    @AfterEach
//    public void tearDown() {
//        driver.quit();
//    }
//
//    public WebElement waitFor(By locator) {
//        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//    }
//}
