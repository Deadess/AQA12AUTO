package lec18;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DragDropPage extends BaseAQAPage {
    @FindBy(id = "manual1")
    private WebElement manual1;

    @FindBy(id = "manual2")
    private WebElement manual2;

    @FindBy(id = "auto1")
    private WebElement auto1;

    @FindBy(id = "auto2")
    private WebElement auto2;

    @FindBy(id = "target-manual1")
    private WebElement targetManual1;

    @FindBy(id = "target-manual2")
    private WebElement targetManual2;

    @FindBy(id = "target-auto1")
    private WebElement targetAuto1;

    @FindBy(id = "target-auto2")
    private WebElement targetAuto2;

    @FindBy(xpath = "//*[contains(text(), \"Congratulations! Let's test for the best!\")]")
    private WebElement successMessage;

    private final Actions actions;

    public DragDropPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.actions = new Actions(this.getCurrentDriver());
        PageFactory.initElements(this.getCurrentDriver(), this);
    }

    public void sortResponsibilities() {
        WebDriver currentDriver = this.getCurrentDriver();
        WebDriverWait currentWait = this.getCurrentWait();

        System.out.println("Sorting responsibilities, wait state: " + (currentWait != null) + ", driver state: " + (currentDriver != null));

        try {
            WebElement sourceManual1 = currentWait.until(ExpectedConditions.visibilityOf(manual1));
            WebElement sourceManual2 = currentWait.until(ExpectedConditions.visibilityOf(manual2));
            WebElement sourceAuto1 = currentWait.until(ExpectedConditions.visibilityOf(auto1));
            WebElement sourceAuto2 = currentWait.until(ExpectedConditions.visibilityOf(auto2));
            WebElement targetManual1 = currentWait.until(ExpectedConditions.visibilityOf(this.targetManual1));
            WebElement targetManual2 = currentWait.until(ExpectedConditions.visibilityOf(this.targetManual2));
            WebElement targetAuto1 = currentWait.until(ExpectedConditions.visibilityOf(this.targetAuto1));
            WebElement targetAuto2 = currentWait.until(ExpectedConditions.visibilityOf(this.targetAuto2));
            actions.dragAndDrop(sourceManual1, targetManual1).perform();
            actions.dragAndDrop(sourceManual2, targetManual2).perform();
            actions.dragAndDrop(sourceAuto1, targetAuto1).perform();
            actions.dragAndDrop(sourceAuto2, targetAuto2).perform();
            WebElement message = currentWait.until(ExpectedConditions.visibilityOf(successMessage));
            assertTrue(message.isDisplayed(), "Success message 'Congratulations! Let's test for the best!' is not displayed");
        } catch (Exception e) {
            System.out.println("Error during drag and drop operations: " + e.getMessage());
            throw new RuntimeException("Failed to sort responsibilities: " + e.getMessage(), e);
        }
    }
}