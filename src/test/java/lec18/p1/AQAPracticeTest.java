package lec18.p1;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.function.BiConsumer;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AQAPracticeTest {
    private static BaseAQAPage basePage;
    private LoginPage loginPage;
    private AQAPracticePage aqaPracticePage;
    private SelectPage selectPage;
    private DragDropPage dragDropPage;
    private ActionsAlertsPage actionsAlertsPage;

    @BeforeAll
    public static void setUpAll() {
        basePage = new BaseAQAPage();
        basePage.setUpStaticDriver();
    }
    @BeforeEach
    public void setUp() {
        basePage = new BaseAQAPage();
        basePage.setUpInstanceDriver();
        loginPage = new LoginPage(basePage.getCurrentDriver(), basePage.getCurrentWait());
        aqaPracticePage = new AQAPracticePage(basePage.getCurrentDriver(), basePage.getCurrentWait());
        selectPage = new SelectPage(basePage.getCurrentDriver(), basePage.getCurrentWait());
        dragDropPage = new DragDropPage(basePage.getCurrentDriver(), basePage.getCurrentWait());
        actionsAlertsPage = new ActionsAlertsPage(basePage.getCurrentDriver(), basePage.getCurrentWait());

        System.out.println("Initializing Page Objects...");
        loginPage.login("e@e", "123123123");
    }

    @AfterEach
    public void tearDown() {
        basePage.tearDownInstanceDriver();
    }

    @AfterAll
    public static void tearDownAll() {
        basePage.tearDownStaticDriver();
    }

    @Test
    void courseSelectTest() {
        System.out.println("Starting courseSelectTest, wait state: " + (basePage.getCurrentWait() != null) + ", driver state: " + (basePage.getCurrentDriver() != null));
        aqaPracticePage.navigateToSelectTestCase();

        String[] dates = selectPage.getNextMondayAndTwoWeeksLater().split(",");
        String startDate = dates[0];
        String endDate = dates[1];

        selectPage.fillCourseSearchForm("USA", "English", "Testing", new String[]{"AQA Java", "AQA Python"}, startDate, endDate);
        assertTrue(selectPage.isNoCoursesMessageDisplayed(), "No courses message is not displayed");
        System.out.println("courseSelectTest completed successfully, wait state: " + (basePage.getCurrentWait() != null) + ", driver state: " + (basePage.getCurrentDriver() != null));
    }

    @Test
    void sortResponsibilitiesTest() {
        System.out.println("Starting sortResponsibilitiesTest, wait state: " + (basePage.getCurrentWait() != null) + ", driver state: " + (basePage.getCurrentDriver() != null));
        aqaPracticePage.navigateToDragDropTestCase();
        dragDropPage.sortResponsibilities();
        System.out.println("sortResponsibilitiesTest completed successfully, wait state: " + (basePage.getCurrentWait() != null) + ", driver state: " + (basePage.getCurrentDriver() != null));
    }

    @ParameterizedTest
    @MethodSource("alertsData")
    void alertsTest(By locator, BiConsumer<Actions, WebElement> action, String alertMessage, String resultMessage, boolean alertInput) {
        basePage.setAlertsTest(true);

        System.out.println("Starting alertsTest for locator: " + locator + ", wait state: " + (basePage.getCurrentWait() != null) + ", driver state: " + (basePage.getCurrentDriver() != null));
        aqaPracticePage.navigateToActionsAlertsTestCase();


        WebDriverWait shortWait = new WebDriverWait(basePage.getCurrentDriver(), Duration.ofSeconds(60));
        By iframeLocator = By.cssSelector("iframe[title='Finish your registration']");
        basePage.getCurrentDriver().switchTo().frame(shortWait.until(ExpectedConditions.visibilityOfElementLocated(iframeLocator)));

        WebElement element = BaseAQAPage.waitFor(locator);
        action.accept(actionsAlertsPage.getActions(), element);

        Alert alert = shortWait.until(ExpectedConditions.alertIsPresent());
        assertTrue(alert.getText().contains(alertMessage), "Alert text mismatch: expected '" + alertMessage + "'");
        if (alertInput) {
            alert.sendKeys("Test");
        }
        alert.accept();

        basePage.getCurrentDriver().switchTo().defaultContent();

        String xpath = "//*[text()='" + resultMessage.replace("'", "\\'").replace("\"", "\\\"") + "']";
        assertTrue(BaseAQAPage.waitFor(By.xpath(xpath)).isDisplayed(), "Result message '" + resultMessage + "' is not displayed");
        System.out.println("alertsTest completed for locator: " + locator + ", wait state: " + (basePage.getCurrentWait() != null) + ", driver state: " + (basePage.getCurrentDriver() != null));
    }

    private static Stream<Arguments> alertsData() {
        return Stream.of(
                Arguments.of(
                        By.id("AlertButton"),
                        (BiConsumer<Actions, WebElement>) (actions, element) -> actions.click(element).perform(),
                        "You have called alert!",
                        "Congratulations, you have successfully enrolled in the course!",
                        false
                ),
                Arguments.of(
                        By.xpath("//*[text()='Get Discount']"),
                        (BiConsumer<Actions, WebElement>) (actions, element) -> actions.doubleClick(element).perform(),
                        "Are you sure you want to apply the discount?",
                        "You received a 10% discount on the second course.",
                        false
                ),
                Arguments.of(
                        By.cssSelector("[data-test-id='PromptButton']"),
                        (BiConsumer<Actions, WebElement>) (actions, element) -> actions.contextClick(element).perform(),
                        "Here you may describe a reason why you are cancelling your registration (or leave this field empty).",
                        "Your course application has been cancelled. Reason: Test",
                        true
                )
        );
    }
}