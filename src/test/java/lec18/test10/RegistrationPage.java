package lec18.test10;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(name = "firstName")
    private WebElement firstNameField;

    @FindBy(name = "lastName")
    private WebElement lastNameField;

    @FindBy(name = "dateOfBirth")
    private WebElement dobField;

    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(name = "passwordConfirmation")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//*[contains(text(), 'Sign Out')]")
    private WebElement signOut;
    @FindBy(xpath = "//*[contains(text(), 'Required')]")
    private WebElement errorMessageReq;

    @FindBy(xpath = "//*[contains(text(), 'Minimum 8 characters')]")
    private WebElement errorMessageMin;

    @FindBy(xpath = "//*[contains(text(), 'Maximum 20 characters')]")
    private WebElement errorMessageMax;

    @FindBy(xpath = "//*[contains(text(), 'Invalid email address')]")
    private WebElement errorMessageEmail;

    @FindBy(xpath = "//*[contains(text(), 'Passwords must match')]")
    private WebElement errorMessagePmm;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://qa-course-01.andersenlab.com/registration");
    }

    public void register(String firstName, String lastName, String dob, String email, String password, String confirmPassword) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        dobField.sendKeys(dob);
        dobField.sendKeys(Keys.ESCAPE);
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(confirmPassword);
        submitButton.click();
    }

    public boolean isLoginPageDisplayed() {
        return driver.getCurrentUrl().contains("login");
    }

    public boolean getErrorMessageSo() {
        wait.until(ExpectedConditions.visibilityOf(signOut));
        return signOut.isDisplayed();
    }
    public boolean getErrorMessageMin() {
        wait.until(ExpectedConditions.visibilityOf(errorMessageMin));
        return errorMessageMin.isDisplayed();
    }
    public boolean getErrorMessageReq() {
        wait.until(ExpectedConditions.visibilityOf(errorMessageReq));
        return errorMessageReq.isDisplayed();
    }

    public boolean getErrorMessageMax() {
        wait.until(ExpectedConditions.visibilityOf(errorMessageMax));
        return errorMessageMax.isDisplayed();
    }

    public boolean getErrorMessageEmail() {
        wait.until(ExpectedConditions.visibilityOf(errorMessageEmail));
        return errorMessageEmail.isDisplayed();
    }

    public boolean getErrorMessagePmm() {
        wait.until(ExpectedConditions.visibilityOf(errorMessagePmm));
        return errorMessagePmm.isDisplayed();
    }
    public boolean isPageLoaded() {
        return driver.getCurrentUrl().contains("registration");
    }
}