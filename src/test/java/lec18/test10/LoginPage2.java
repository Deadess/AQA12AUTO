package lec18.test10;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage2 {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//*[contains(text(), 'Required')]")
    private WebElement errorMessageReq;

    @FindBy(xpath = "//*[contains(text(), 'Minimum 8 characters')]")
    private WebElement errorMessageMin;

    @FindBy(xpath = "//*[contains(text(), 'Maximum 20 characters')]")
    private WebElement errorMessageMax;

    @FindBy(xpath = "//*[contains(text(), 'Invalid email address')]")
    private WebElement errorMessageEmail;

    @FindBy(xpath = "//*[contains(text(), 'Email or password is not valid')]")
    private WebElement errorMessageLogin;


    @FindBy(xpath = "//*[contains(text(), 'Sign Out')]")
    private WebElement signOut;

    public LoginPage2(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://qa-course-01.andersenlab.com/login");
        wait.until(ExpectedConditions.visibilityOf(emailField));
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(email);
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public boolean isDashboardDisplayed() {
        wait.until(ExpectedConditions.visibilityOf(signOut));
        return signOut.isDisplayed();
    }
    public boolean getErrorMessageLogin() {
        wait.until(ExpectedConditions.visibilityOf(errorMessageLogin));
        return errorMessageLogin.isDisplayed();
    }

}