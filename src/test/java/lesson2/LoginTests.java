package lesson2;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.Assert.assertTrue;

public class LoginTests extends BaseTest{
    By signOutLocator = By.cssSelector("[alt=\"Sign Out\"]");
    By errorLocator = By.xpath("//*[contains(text(), 'Email or password is not valid')]");
    WebElement signOutElement;
    WebElement errorElement;

    @Test
    public void testValidLogin() {
        login("e@e", "123123123");
        signOutElement = wait.until(ExpectedConditions.visibilityOfElementLocated(signOutLocator));
        assertTrue(signOutElement.isDisplayed());
    }
    @Test
    public void testInvalidLogin() {
        login("a@a", "321321321");
        errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator));
        assertTrue(errorElement.isDisplayed());
    }
    @After
    public void tearDown() {
        driver.quit();
    }
}