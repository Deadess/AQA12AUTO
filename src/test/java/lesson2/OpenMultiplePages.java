package lesson2;

import org.junit.Test;
import org.openqa.selenium.WindowType;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class OpenMultiplePages extends BaseTest{
    String[] urls = {
        "http://www.automationpractice.pl/index.php",
        "https://zoo.waw.pl/",
        "https://www.w3schools.com/",
        "https://www.clickspeedtester.com/click-counter/",
        "https://andersenlab.com/",
    };

    @Test
    public void multiplePagesTest() {
        for(String url : urls) {
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get(url);
        }
        List<String> windows = new ArrayList<>(driver.getWindowHandles());
        for (String window : windows) {
            driver.switchTo().window(window);
            String title = driver.getTitle();
            String url = driver.getCurrentUrl();
            System.out.println("Title: " + title);
            System.out.println("URL: " + url);
            if(title.contains("Zoo")) {
                driver.close();
                System.out.println("Zoo is over");
            }
        }
        assertTrue( driver.getWindowHandles().stream().count() == urls.length);
    }
}
