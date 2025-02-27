package lec18;

import lec18.pages.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.function.BiConsumer;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AQAPracticeTest extends BaseTest {
    private LoginPage loginPage;
    private AQAPracticePage aqaPracticePage;
    private SelectPage selectPage;
    private DragDropPage dragDropPage;
    private ActionsAlertsPage actionsAlertsPage;
    @BeforeEach
    public void setUp() {
        super.setUp();
        loginPage = new LoginPage(driver, wait);
        aqaPracticePage = new AQAPracticePage(driver, wait);
        selectPage = new SelectPage(driver, wait);
        dragDropPage = new DragDropPage(driver, wait);
        actionsAlertsPage = new ActionsAlertsPage(driver, wait);
        loginPage.login("e@e", "123123123");
    }

    @Test
    void courseSelectTest() {
        aqaPracticePage.navigateToSelectTestCase();

        String[] dates = selectPage.getNextMondayAndTwoWeeksLater().split(",");
        String startDate = dates[0];
        String endDate = dates[1];

        selectPage.fillCourseSearchForm("USA", "English", "Testing", new String[]{"AQA Java", "AQA Python"}, startDate, endDate);
        assertTrue(selectPage.isNoCoursesMessageDisplayed(), "No courses message is not displayed");
    }

    @Test
    void sortResponsibilitiesTest() {
        aqaPracticePage.navigateToDragDropTestCase();
        dragDropPage.sortResponsibilities();
    }

    @ParameterizedTest
    @MethodSource("alertsData")
    void alertsTest(By locator, BiConsumer<Actions, WebElement> action, String alertMessage, String resultMessage, boolean alertInputExpected) {
        aqaPracticePage.navigateToActionsAlertsTestCase();
        actionsAlertsPage.navigateToFrame();

        WebElement element = waitFor(locator);
        action.accept(actionsAlertsPage.getActions(), element);

        actionsAlertsPage
            .checkAlert(alertMessage, alertInputExpected)
            .checkResultMessage(resultMessage);
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