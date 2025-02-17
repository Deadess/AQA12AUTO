package lesson2;

import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.nio.file.Paths;

public class ChangeProfilePicture extends BaseTest {
    String photoPath = Paths.get(System.getProperty("user.dir"), "src/test/resources/photo_knight.jpg").toAbsolutePath().toString();
    //locators
    By profilePhotoInputLocator = By.xpath("//input[@accept=\"image/*\"]");
    By profilePhotoPopupCloseLocator = By.xpath("//img[@alt='Close']");
    WebElement closeButton;

    @Test
    public void profilePhotoUploadTest() {
        //actions
        driver.get(baseUrl);
        loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(emailLocator));
        loginField.sendKeys("aa@a");
        passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLocator));
        passwordField.sendKeys("123123123");
        loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
        loginButton.click();
        WebElement profilePhotoInput = wait.until(ExpectedConditions.presenceOfElementLocated(profilePhotoInputLocator));
        profilePhotoInput.sendKeys(photoPath);
        closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(profilePhotoPopupCloseLocator));
        closeButton.click();
        driver.quit();
    }
}
