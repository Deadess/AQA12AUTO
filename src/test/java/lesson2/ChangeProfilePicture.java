package lesson2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.nio.file.Paths;
import java.time.Duration;

public class ChangeProfilePicture {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://qa-course-01.andersenlab.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        loginField.sendKeys("aa@a");
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("123123123");
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        loginButton.click();
        WebElement profilePhoto = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='root']/div/div[2]/div/main/div/section/div/div[1]/div[1]")));
        profilePhoto.click();
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(1000);
            String photoPath = Paths.get(System.getProperty("user.dir"), "src", "test", "resources", "photo_knight.jpg").toAbsolutePath().toString();
            StringSelection stringSelection = new StringSelection(photoPath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        WebElement closeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='Close']")));
        closeButton.click();
        driver.quit();
    }
}
