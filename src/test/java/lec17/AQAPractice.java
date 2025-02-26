//package lec17;
//
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.openqa.selenium.Alert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.Select;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.function.BiConsumer;
//import java.util.stream.Stream;
//
//import static org.junit.Assert.assertTrue;
//
//public class AQAPractice extends Base {
//    By aqaPracticeLocator = By.xpath("//*[@class='my-auto' and text() = 'AQA Practice']");
//    By aqaPracticeSelectLocator = By.xpath("//*[text()=\"Select\"]");
//    By aqaPracticeDragDropLocator = By.xpath("//*[text()=\"Drag & Drop\"]");
//    By aqaPracticeActionsLocator = By.xpath("//*[text()=\"Actions, Alerts & Iframes\"]");
//
//    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//
//    Actions actions;
//
//    @Override
//    @BeforeEach
//    public void setUp() {
//        super.setUp();
//        login("e@e", "123123123");
//        actions = new Actions(driver);
//    }
//
//    @Test
//    public void courseSelectTest() {
//        By countrySelectLocator = By.cssSelector("select[title=\"Select country\"]");
//        By languageSelectLocator = By.cssSelector("select[title=\"Select language\"]");
//        By typeSelectLocator = By.cssSelector("select[title=\"Select type\"]");
//        By courseMultiSelectLocator = By.id("MultipleSelect");
//        By fromDateLocator = By.cssSelector("input[title=\"Start date\"]");
//        By toDateLocator = By.cssSelector("input[title=\"End date\"]");
//        By submitLocator = By.name("SelectPageSearchButton");
//        By messageLocator = By.xpath("//*[text()=\"Unfortunately, we did not find any courses matching your chosen criteria.\"]");
//        LocalDate nextMonday = LocalDate.now().plusDays( 7 - LocalDate.now().getDayOfWeek().ordinal());
//        String startDate = nextMonday.format(dateFormat);
//        String endDate = nextMonday.plusWeeks(2).format(dateFormat);
//
//        navigateToTestCase(aqaPracticeSelectLocator);
//
//        //set fields values
//        new Select(waitFor(countrySelectLocator)).selectByVisibleText("USA");
//        new Select(waitFor(languageSelectLocator)).selectByVisibleText("English");
//        new Select(waitFor(typeSelectLocator)).selectByVisibleText("Testing");
//        waitFor(fromDateLocator).sendKeys(startDate);
//        waitFor(toDateLocator).sendKeys(endDate);
//        new Select(waitFor(courseMultiSelectLocator)).selectByVisibleText("AQA Java");
//        new Select(waitFor(courseMultiSelectLocator)).selectByVisibleText("AQA Python");
//
//        waitFor(submitLocator).click();
//
//        //check message
//        assertTrue(waitFor(messageLocator).isDisplayed());
//    }
//
//    @Test
//    public void sortResponsibilitiesTest() {
//        By manual1Locator = By.id("manual1");
//        By manual2Locator = By.id("manual2");
//        By auto1Locator = By.id("auto1");
//        By auto2Locator = By.id("auto2");
//        By target1Locator = By.id("target-manual1");
//        By target2Locator = By.id("target-manual2");
//        By target3Locator = By.id("target-auto1");
//        By target4Locator = By.id("target-auto2");
//        By messageLocator = By.xpath("//*[text()=\"Congratulations! Let's test for the best!\"]");
//
//        //drag and drop
//        navigateToTestCase(aqaPracticeDragDropLocator);
//        actions.dragAndDrop(waitFor(manual1Locator), waitFor(target1Locator)).perform();
//        actions.dragAndDrop(waitFor(manual2Locator), waitFor(target2Locator)).perform();
//        actions.dragAndDrop(waitFor(auto1Locator), waitFor(target3Locator)).perform();
//        actions.dragAndDrop(waitFor(auto2Locator), waitFor(target4Locator)).perform();
//
//        //check message
//        WebElement message = waitFor(messageLocator);
//        assertTrue(message.isDisplayed());
//    }
//
//    @ParameterizedTest
//    @MethodSource("alertsData")
//    void alertsTest(By locator, BiConsumer<Actions, WebElement> action, String alertMessage, String resultMessage, boolean alertInput) {
//        By iframeLocator = By.cssSelector("iframe[title='Finish your registration']");
//
//        navigateToTestCase(aqaPracticeActionsLocator);
//        //trigger alert
//        driver.switchTo().frame(waitFor(iframeLocator));
//        action.accept(actions, waitFor(locator));
//        //check alert
//        Alert alert = driver.switchTo().alert();
//
//        if(alertInput) {
//            alert.sendKeys("Test");
//        }
//
//        assertTrue(alert.getText().contains(alertMessage));
//        alert.accept();
//        //check message
//        assertTrue(waitFor(By.xpath("//*[text()='" + resultMessage + "']")).isDisplayed());
//    }
//
//    void navigateToTestCase(By targetTC) {
//        waitFor(aqaPracticeLocator).click();
//        waitFor(targetTC).click();
//    }
//
//    private static Stream<Arguments> alertsData() {
//        return Stream.of(
//                Arguments.of(
//                        By.id("AlertButton"),
//                        (BiConsumer<Actions, WebElement>) (actions, element) -> actions.click(element).perform(),
//                        "You have called alert!",
//                        "Congratulations, you have successfully enrolled in the course!",
//                        false
//                ),
//                Arguments.of(
//                        By.xpath("//*[text()='Get Discount']"),
//                        (BiConsumer<Actions, WebElement>) (actions, element) -> actions.doubleClick(element).perform(),
//                        "Are you sure you want to apply the discount?",
//                        "You received a 10% discount on the second course.",
//                        false
//                ),
//                Arguments.of(
//                        By.cssSelector("[data-test-id=PromptButton]"),
//                        (BiConsumer<Actions, WebElement>) (actions, element) -> actions.contextClick(element).perform(),
//                        "Here you may describe a reason why you are cancelling your registration (or leave this field empty).",
//                        "Your course application has been cancelled. Reason: Test",
//                        true
//                )
//        );
//    }
//}
