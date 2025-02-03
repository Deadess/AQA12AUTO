package lesson2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegistrationTests extends BaseTest{
    Actions actions;

    String login;
    By fNameLocator = By.name("firstName");
    By lNameLocator = By.name("lastName");
    By dateOfBirthLocator = By.name("dateOfBirth");
    By emailLocator = By.name("email");
    By passwordLocator = By.name("password");
    By passwordConfirmationLocator = By.name("passwordConfirmation");
    By submitButtonLocator = By.xpath("//button[@type='submit']");
    By errorLocator = By.xpath("//*[contains(text(), 'Minimum 8 characters')]");

    WebElement fName;
    WebElement lName;
    WebElement dateOfBirth;
    WebElement email;
    WebElement pword;
    WebElement pwordConfirmation;
    WebElement submitButton;
    WebElement errorMessage;

    @Before
    public void setUpActions() {
        login = "a+"+ LocalDateTime.now().getNano() +"@a";
        actions = new Actions(driver);
        driver.get("https://qa-course-01.andersenlab.com/registration");
    }

    @Test
    public void testValidRegistration() {
        fName = wait.until(ExpectedConditions.visibilityOfElementLocated(fNameLocator));
        fName.sendKeys("John");
        lName = wait.until(ExpectedConditions.visibilityOfElementLocated(lNameLocator));
        lName.sendKeys("Doe");
        dateOfBirth = wait.until(ExpectedConditions.visibilityOfElementLocated(dateOfBirthLocator));
        dateOfBirth.sendKeys("05/04/2000");
        actions.sendKeys(Keys.ESCAPE).perform();
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(emailLocator));
        email.sendKeys(login);
        pword = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator));
        pword.sendKeys("123123123");
        pwordConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordConfirmationLocator));
        pwordConfirmation.sendKeys("123123123");
        submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
        submitButton.click();
//        WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
//        assertTrue(loginField.isDisplayed());
        wait.until(ExpectedConditions.urlContains("/login"));
    }
    @Test
    public void testShortPasswordRegistration() {
        fName = wait.until(ExpectedConditions.visibilityOfElementLocated(fNameLocator));
        fName.sendKeys("John");
        lName = wait.until(ExpectedConditions.visibilityOfElementLocated(lNameLocator));
        lName.sendKeys("Doe");
        dateOfBirth = wait.until(ExpectedConditions.visibilityOfElementLocated(dateOfBirthLocator));
        dateOfBirth.sendKeys("05/04/2000");
        actions.sendKeys(Keys.ESCAPE).perform();
        email = wait.until(ExpectedConditions.visibilityOfElementLocated(emailLocator));
        email.sendKeys("aaaa@a");
        pword = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator));
        pword.sendKeys("123");
        pwordConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordConfirmationLocator));
        pwordConfirmation.sendKeys("123");
        errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator));
        assertTrue(errorMessage.isDisplayed());
    }
}