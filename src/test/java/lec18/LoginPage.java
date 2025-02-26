package lec18;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BaseAQAPage {
    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(this.getCurrentDriver(), this);
    }

    public void login(String login, String password) {
        WebDriver currentDriver = this.getCurrentDriver();
        WebDriverWait currentWait = this.getCurrentWait();

        System.out.println("Attempting login with " + login + " / " + password + ", wait state: " + (currentWait != null) + ", driver state: " + (currentDriver != null));
        currentWait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(login);
        currentWait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
        currentWait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        System.out.println("Login button clicked, waiting for page load, wait state: " + (currentWait != null) + ", driver state: " + (currentDriver != null));
        currentWait.until(ExpectedConditions.urlContains("/login"));
        System.out.println("Login completed, current URL: " + currentDriver.getCurrentUrl() + ", wait state: " + (currentWait != null) + ", driver state: " + (currentDriver != null));
    }
}