package lesson2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class LoginAutomationScript {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://qa-course-01.andersenlab.com/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        loginField.sendKeys("a@a");
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys("123123123");
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));
        loginButton.click();
        WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='root']/div/div[2]/div/main/div/section/div/div[2]/div[1]/div/div[1]/div/div[1]")));
        if (dashboard.isDisplayed()) {
            System.out.println("Authorisation success.");
        } else {
            System.out.println("Authorisation denied.");
        }
        driver.quit();
    }
}
