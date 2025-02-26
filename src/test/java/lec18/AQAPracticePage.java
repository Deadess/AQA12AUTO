package lec18;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AQAPracticePage extends BaseAQAPage {
    @FindBy(xpath = "//*[@class='my-auto' and text() = 'AQA Practice']")
    private WebElement aqaPracticeLink;

    @FindBy(xpath = "//*[text()='Select']")
    private WebElement selectLink;

    @FindBy(xpath = "//*[text()='Drag & Drop']")
    private WebElement dragDropLink;

    @FindBy(xpath = "//*[text()='Actions, Alerts & Iframes']")
    private WebElement actionsAlertsLink;

    public AQAPracticePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(this.getCurrentDriver(), this);
    }

    public void navigateToSelectTestCase() {
        WebDriver currentDriver = this.getCurrentDriver();
        WebDriverWait currentWait = this.getCurrentWait();

        currentWait.until(ExpectedConditions.visibilityOf(aqaPracticeLink)).click();
        currentWait.until(ExpectedConditions.visibilityOf(selectLink)).click();
        System.out.println("Navigated to Select Test Case, current URL: " + currentDriver.getCurrentUrl() + ", wait state: " + (currentWait != null) + ", driver state: " + (currentDriver != null));
    }

    public void navigateToDragDropTestCase() {
        WebDriver currentDriver = this.getCurrentDriver();
        WebDriverWait currentWait = this.getCurrentWait();

        currentWait.until(ExpectedConditions.visibilityOf(aqaPracticeLink)).click();
        currentWait.until(ExpectedConditions.visibilityOf(dragDropLink)).click();
        System.out.println("Navigated to Drag & Drop Test Case, current URL: " + currentDriver.getCurrentUrl() + ", wait state: " + (currentWait != null) + ", driver state: " + (currentDriver != null));
    }

    public void navigateToActionsAlertsTestCase() {
        WebDriver currentDriver = this.getCurrentDriver();
        WebDriverWait currentWait = this.getCurrentWait();

        currentWait.until(ExpectedConditions.visibilityOf(aqaPracticeLink)).click();
        currentWait.until(ExpectedConditions.visibilityOf(actionsAlertsLink)).click();
        System.out.println("Navigated to Actions, Alerts & Iframes Test Case, current URL: " + currentDriver.getCurrentUrl() + ", wait state: " + (currentWait != null) + ", driver state: " + (currentDriver != null));
    }
}