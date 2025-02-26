package lec18.p1;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActionsAlertsPage extends BaseAQAPage {
    @FindBy(css = "iframe[title='Finish your registration']")
    private WebElement iframe;

    @FindBy(id = "AlertButton")
    private WebElement alertButton;

    @FindBy(xpath = "//*[text()='Get Discount']")
    private WebElement getDiscountButton;

    @FindBy(css = "[data-test-id='PromptButton']")
    private WebElement promptButton;

    private final Actions actions;

    public ActionsAlertsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait); // Вызываем конструктор родительского класса с параметрами
        this.actions = new Actions(this.getCurrentDriver());
        PageFactory.initElements(this.getCurrentDriver(), this); // Инициализация с текущим driver
    }

    public Actions getActions() {
        return actions;
    }

    public void performAlertAction(By locator, BiConsumer<Actions, WebElement> action, String inputText, String expectedAlertMessage) {
        WebDriver currentDriver = this.getCurrentDriver();
        WebDriverWait currentWait = this.getCurrentWait();

        // Прямое переключение на iframe с явным ожиданием и увеличенным тайм-аутом (60 секунд для alertsTest)
        WebDriverWait shortWait = new WebDriverWait(currentDriver, Duration.ofSeconds(60)); // Увеличили для стабильности
        WebElement iframeElement = shortWait.until(ExpectedConditions.visibilityOf(iframe));
        currentDriver.switchTo().frame(iframeElement);

        WebElement element;
        try {
            if (locator.equals(By.id("AlertButton"))) {
                element = currentWait.until(ExpectedConditions.visibilityOf(alertButton));
            } else if (locator.equals(By.xpath("//*[text()='Get Discount']"))) {
                element = currentWait.until(ExpectedConditions.visibilityOf(getDiscountButton));
            } else if (locator.equals(By.cssSelector("[data-test-id='PromptButton']"))) {
                element = currentWait.until(ExpectedConditions.visibilityOf(promptButton));
            } else {
                element = currentWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            }
            action.accept(actions, element);
        } catch (Exception e) {
            currentDriver.switchTo().defaultContent(); // Возвращаемся к основному окну в случае ошибки
            throw e;
        }

        Alert alert;
        try {
            alert = shortWait.until(ExpectedConditions.alertIsPresent());
            assertTrue(alert.getText().contains(expectedAlertMessage), "Alert text mismatch: expected '" + expectedAlertMessage + "'");
            if (inputText != null) {
                alert.sendKeys(inputText);
            }
            alert.accept();
        } catch (Exception e) {
            currentDriver.switchTo().defaultContent(); // Возвращаемся к основному окну в случае ошибки
            throw e;
        }

        currentDriver.switchTo().defaultContent();
        System.out.println("Performed action with locator: " + locator + ", handled alert, wait state: " + (currentWait != null) + ", driver state: " + (currentDriver != null));
    }

    public boolean isResultMessageDisplayed(String resultMessage) {
        WebDriverWait currentWait = this.getCurrentWait();

        try {
            // Обновлённый локатор с корректными кавычками для текста сообщения
            String xpath = "//*[text()='" + resultMessage.replace("'", "\\'").replace("\"", "\\\"") + "']";
            WebElement message = currentWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            return message.isDisplayed();
        } catch (Exception e) {
            System.out.println("Failed to find result message '" + resultMessage + "' with visibility check: " + e.getMessage());
            try {
                // Резервный локатор с contains(text())
                String xpathFallback = "//*[contains(text(), '" + resultMessage.replace("'", "\\'").replace("\"", "\\\"") + "')]";
                WebElement message = currentWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathFallback)));
                return message.isDisplayed();
            } catch (Exception ex) {
                System.out.println("Failed to find result message '" + resultMessage + "' with presence check: " + ex.getMessage());
                return false;
            }
        }
    }
}