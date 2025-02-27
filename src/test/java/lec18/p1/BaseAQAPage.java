package lec18.p1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseAQAPage {
    private static WebDriver staticDriver;
    private static WebDriverWait staticWait;
    protected WebDriver instanceDriver;
    protected WebDriverWait instanceWait;

    protected static String baseUrl = "https://qa-course-01.andersenlab.com/";
    @FindBy(name = "email")
    private WebElement emailField;
    @FindBy(name = "password")
    private WebElement passwordField;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;
    private boolean isAlertsTest = false;

    @BeforeAll
    public static void setUpStaticDriver() {
        System.out.println("Initializing static ChromeDriver with WebDriverManager for alertsTest...");
        try {
            WebDriverManager.chromedriver().setup();
            staticDriver = new ChromeDriver();
            System.out.println("Static ChromeDriver initialized successfully, version: " + ((ChromeDriver) staticDriver).getCapabilities().getBrowserVersion());
            staticWait = new WebDriverWait(staticDriver, Duration.ofSeconds(60));
            System.out.println("Static WebDriverWait initialized successfully, wait object: " + staticWait + ", driver: " + staticDriver);
            staticDriver.get(baseUrl);
            System.out.println("Navigated to: " + baseUrl + ", static driver state: " + (staticDriver != null) + ", static wait state: " + (staticWait != null));
        } catch (Exception e) {
            System.out.println("Failed to initialize static ChromeDriver: " + e.getMessage());
            throw new RuntimeException("Static ChromeDriver initialization failed: " + e.getMessage(), e);
        }
    }

    @BeforeEach
    public void setUpInstanceDriver() {
        System.out.println("Test starting in BaseAQAPage.setUpInstanceDriver, current instance wait: " + instanceWait + ", current instance driver: " + instanceDriver);
        System.out.println("Initializing instance ChromeDriver with WebDriverManager for non-alerts tests...");
        try {
            WebDriverManager.chromedriver().setup();
            instanceDriver = new ChromeDriver();
            System.out.println("Instance ChromeDriver initialized successfully, version: " + ((ChromeDriver) instanceDriver).getCapabilities().getBrowserVersion());
            instanceWait = new WebDriverWait(instanceDriver, Duration.ofSeconds(10));
            System.out.println("Instance WebDriverWait initialized successfully, wait object: " + instanceWait + ", driver: " + instanceDriver);
            instanceDriver.get(baseUrl);
            System.out.println("Navigation completed, instance driver state: " + (instanceDriver != null) + ", instance wait state: " + (instanceWait != null));
        } catch (Exception e) {
            System.out.println("Failed to initialize instance ChromeDriver: " + e.getMessage());
            throw new RuntimeException("Instance ChromeDriver initialization failed: " + e.getMessage(), e);
        }
    }

    public void login(String login, String password) {
        WebDriver currentDriver = isAlertsTest ? staticDriver : instanceDriver;
        WebDriverWait currentWait = isAlertsTest ? staticWait : instanceWait;

        System.out.println("Attempting login with " + login + " / " + password + ", wait state: " + (currentWait != null) + ", driver state: " + (currentDriver != null));
        currentWait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(login);
        currentWait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
        currentWait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        System.out.println("Login button clicked, waiting for page load, wait state: " + (currentWait != null) + ", driver state: " + (currentDriver != null));
        currentWait.until(ExpectedConditions.urlContains("/login"));
        System.out.println("Login completed, current URL: " + currentDriver.getCurrentUrl() + ", wait state: " + (currentWait != null) + ", driver state: " + (currentDriver != null));
    }

    @AfterEach
    public void tearDownInstanceDriver() {
        if (instanceDriver != null && !isAlertsTest) {
            System.out.println("Closing instance driver, current instance wait state: " + (instanceWait != null) + ", instance driver state: " + (instanceDriver != null));
            try {
                instanceDriver.quit();
            } catch (Exception e) {
                System.out.println("Failed to close instance driver: " + e.getMessage());
            }
            instanceDriver = null;
            instanceWait = null;
        }
    }

    @AfterAll
    public static void tearDownStaticDriver() {
        if (staticDriver != null) {
            System.out.println("Closing static driver, current static wait state: " + (staticWait != null) + ", static driver state: " + (staticDriver != null));
            try {
                staticDriver.quit();
            } catch (Exception e) {
                System.out.println("Failed to close static driver: " + e.getMessage());
            }
            staticDriver = null;
            staticWait = null;
        }
    }

    public static WebElement waitFor(By locator) {
        if (staticWait == null || staticDriver == null) {
            throw new IllegalStateException("Static WebDriver or WebDriverWait is null. Check BaseAQAPage.setUpStaticDriver initialization.");
        }
        try {
            System.out.println("Waiting for visibility of element: " + locator + ", static wait object: " + staticWait + ", static driver: " + staticDriver);
            return staticWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Failed to find visible element: " + locator + ", Error: " + e.getMessage() + ", static wait: " + (staticWait != null) + ", static driver: " + (staticDriver != null));
            throw e;
        }
    }

    public static WebElement waitFor(WebElement element) {
        if (staticWait == null || staticDriver == null) {
            throw new IllegalStateException("Static WebDriver or WebDriverWait is null. Check BaseAQAPage.setUpStaticDriver initialization.");
        }
        try {
            System.out.println("Waiting for visibility of element: " + element + ", static wait object: " + staticWait + ", static driver: " + staticDriver);
            return staticWait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            System.out.println("Failed to find visible element: " + element + ", Error: " + e.getMessage() + ", static wait: " + (staticWait != null) + ", static driver: " + (staticDriver != null));
            throw e;
        }
    }

    public static WebElement waitForClickable(By locator) {
        if (staticWait == null || staticDriver == null) {
            throw new IllegalStateException("Static WebDriver or WebDriverWait is null. Check BaseAQAPage.setUpStaticDriver initialization.");
        }
        try {
            System.out.println("Waiting for clickable element: " + locator + ", static wait object: " + staticWait + ", static driver: " + staticDriver);
            return staticWait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            System.out.println("Failed to find clickable element: " + locator + ", Error: " + e.getMessage() + ", static wait: " + (staticWait != null) + ", static driver: " + (staticDriver != null));
            throw e;
        }
    }

    protected WebElement instanceWaitFor(By locator) {
        if (instanceWait == null || instanceDriver == null) {
            throw new IllegalStateException("Instance WebDriver or WebDriverWait is null. Check BaseAQAPage.setUpInstanceDriver initialization.");
        }
        try {
            System.out.println("Waiting for visibility of element: " + locator + ", instance wait object: " + instanceWait + ", instance driver: " + instanceDriver);
            return instanceWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (Exception e) {
            System.out.println("Failed to find visible element: " + locator + ", Error: " + e.getMessage() + ", instance wait: " + (instanceWait != null) + ", instance driver: " + (instanceDriver != null));
            throw e;
        }
    }

    protected WebElement instanceWaitFor(WebElement element) {
        if (instanceWait == null || instanceDriver == null) {
            throw new IllegalStateException("Instance WebDriver or WebDriverWait is null. Check BaseAQAPage.setUpInstanceDriver initialization.");
        }
        try {
            System.out.println("Waiting for visibility of element: " + element + ", instance wait object: " + instanceWait + ", instance driver: " + instanceDriver);
            return instanceWait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            System.out.println("Failed to find visible element: " + element + ", Error: " + e.getMessage() + ", instance wait: " + (instanceWait != null) + ", instance driver: " + (instanceDriver != null));
            throw e;
        }
    }

    protected WebElement instanceWaitForClickable(By locator) {
        if (instanceWait == null || instanceDriver == null) {
            throw new IllegalStateException("Instance WebDriver or WebDriverWait is null. Check BaseAQAPage.setUpInstanceDriver initialization.");
        }
        try {
            System.out.println("Waiting for clickable element: " + locator + ", instance wait object: " + instanceWait + ", instance driver: " + instanceDriver);
            return instanceWait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            System.out.println("Failed to find clickable element: " + locator + ", Error: " + e.getMessage() + ", instance wait: " + (instanceWait != null) + ", instance driver: " + (instanceDriver != null));
            throw e;
        }
    }
    public void setAlertsTest(boolean isAlertsTest) {
        this.isAlertsTest = isAlertsTest;
    }
    public WebDriver getCurrentDriver() {
        return isAlertsTest ? staticDriver : instanceDriver;
    }
    public WebDriverWait getCurrentWait() {
        return isAlertsTest ? staticWait : instanceWait;
    }
    public BaseAQAPage() {
        this.instanceDriver = instanceDriver;
        this.instanceWait = instanceWait;
        if (this.getCurrentDriver() != null) {
            PageFactory.initElements(this.getCurrentDriver(), this);
        }
    }
    public BaseAQAPage(WebDriver driver, WebDriverWait wait) {
        this.instanceDriver = driver != null ? driver : instanceDriver;
        this.instanceWait = wait != null ? wait : instanceWait;
        if (this.getCurrentDriver() != null) {
            PageFactory.initElements(this.getCurrentDriver(), this);
        }
    }
}