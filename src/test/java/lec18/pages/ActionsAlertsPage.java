package lec18.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActionsAlertsPage extends BasePage {
    @FindBy(css = "iframe[title='Finish your registration']")
    private WebElement iframe;

    @FindBy(id = "AlertButton")
    private WebElement alertButton;

    @FindBy(xpath = "//*[text()='Get Discount']")
    private WebElement getDiscountButton;

    @FindBy(css = "[data-test-id='PromptButton']")
    private WebElement promptButton;

    Alert alert;

    private final Actions actions;

    public ActionsAlertsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public Actions getActions() {
        return actions;
    }

    public void navigateToFrame() {
        driver.switchTo().frame(iframe);
    }

    public ActionsAlertsPage checkAlert(String message, boolean alertInputExpected) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        assertTrue(alert.getText().contains(message), "Alert text mismatch: expected '" + message + "'");
        if (alertInputExpected) {
            alert.sendKeys("Test");
        }
        alert.accept();
        return this;
    }

    public ActionsAlertsPage checkResultMessage(String message) {
        By resultLocator = By.xpath("//*[text()='" + message + "']");
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(resultLocator)).isDisplayed(), "Result message '" + message + "' is not displayed");
        return this;
    }
}