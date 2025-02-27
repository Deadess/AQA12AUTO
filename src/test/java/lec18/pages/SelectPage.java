package lec18.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SelectPage extends BasePage {
    @FindBy(css = "select[title='Select country']")
    private WebElement countrySelect;

    @FindBy(css = "select[title='Select language']")
    private WebElement languageSelect;

    @FindBy(css = "select[title='Select type']")
    private WebElement typeSelect;

    @FindBy(id = "MultipleSelect")
    private WebElement courseMultiSelect;

    @FindBy(css = "input[title='Start date']")
    private WebElement fromDate;

    @FindBy(css = "input[title='End date']")
    private WebElement toDate;

    @FindBy(name = "SelectPageSearchButton")
    private WebElement searchButton;

    @FindBy(xpath = "//*[contains(text(), 'Unfortunately, we did not find any courses matching your chosen criteria.')]")
    private WebElement noCoursesMessage;

    public SelectPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public void fillCourseSearchForm(String country, String language, String type, String[] courses, String startDate, String endDate) {
        WebElement countryElement = wait.until(ExpectedConditions.visibilityOf(countrySelect));
        new Select(countryElement).selectByVisibleText(country);

        WebElement languageElement = wait.until(ExpectedConditions.visibilityOf(languageSelect));
        new Select(languageElement).selectByVisibleText(language);

        WebElement typeElement = wait.until(ExpectedConditions.visibilityOf(typeSelect));
        new Select(typeElement).selectByVisibleText(type);

        WebElement courseElement = wait.until(ExpectedConditions.visibilityOf(courseMultiSelect));
        Select multiSelect = new Select(courseElement);
        for (String course : courses) {
            multiSelect.selectByVisibleText(course);
        }
        WebElement fromDateElement = wait.until(ExpectedConditions.visibilityOf(fromDate));
        fromDateElement.sendKeys(startDate);
        WebElement toDateElement = wait.until(ExpectedConditions.visibilityOf(toDate));
        toDateElement.sendKeys(endDate);
        WebElement searchButtonElement = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButtonElement.click();
    }

    public boolean isNoCoursesMessageDisplayed() {
        try {
            WebElement message = wait.until(ExpectedConditions.visibilityOf(noCoursesMessage));
            return message.isDisplayed();
        } catch (Exception e) {
            System.out.println("Failed to find no courses message: " + e.getMessage());
            return false;
        }
    }

    public String getNextMondayAndTwoWeeksLater() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate nextMonday = LocalDate.now().plusDays(7 - LocalDate.now().getDayOfWeek().getValue());
        String startDate = nextMonday.format(dateFormat);
        String endDate = nextMonday.plusWeeks(2).format(dateFormat);
        return startDate + "," + endDate;
    }
}