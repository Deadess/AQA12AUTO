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
import static org.junit.Assert.assertTrue;

public class RegistrationTests {
    private WebDriver driver;
    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://qa-course-01.andersenlab.com/registration");
    }
    @Test
    public void testValidRegistration() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement fName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName")));
        fName.sendKeys("John");
        WebElement lName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
        lName.sendKeys("Doe");
        WebElement dateOfBirth = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("dateOfBirth")));
        dateOfBirth.sendKeys("05/04/2000");
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        email.sendKeys("aaaa@a");
        WebElement pword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        pword.sendKeys("123123123");
        WebElement pwordConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("passwordConfirmation")));
        pwordConfirmation.sendKeys("123123123");
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        submitButton.click();
        WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        assertTrue(loginField.isDisplayed());
    }
    @Test
    public void testShortPasswordRegistration() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement fName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("firstName")));
        fName.sendKeys("John");
        WebElement lName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("lastName")));
        lName.sendKeys("Doe");
        WebElement dateOfBirth = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("dateOfBirth")));
        dateOfBirth.sendKeys("05/04/2000");
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        email.sendKeys("aaaa@a");
        WebElement pword = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        pword.sendKeys("123");
        WebElement pwordConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("passwordConfirmation")));
        pwordConfirmation.sendKeys("123");
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Minimum 8 characters')]")));
        assertTrue(errorMessage.isDisplayed());
    }
    @After
    public void tearDown() {
        driver.quit();
    }
}