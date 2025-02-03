package lesson2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import java.util.ArrayList;
import java.util.List;

public class OpenMultiplePages {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("http://www.automationpractice.pl/index.php");
        js.executeScript("window.open('https://zoo.waw.pl/', '_blank');");
        js.executeScript("window.open('https://www.w3schools.com/', '_blank');");
        js.executeScript("window.open('https://www.clickspeedtester.com/click-counter/', '_blank');");
        js.executeScript("window.open('https://andersenlab.com/', '_blank');");
        List<String> windows = new ArrayList<>(driver.getWindowHandles());
        for (String window : windows) {
            driver.switchTo().window(window);
            String title = driver.getTitle();
            String url = driver.getCurrentUrl();
            System.out.println("Title: " + title);
            System.out.println("URL: " + url);
        }
        driver.quit();
    }
}
