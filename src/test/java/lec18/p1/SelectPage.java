package lec18.p1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SelectPage extends BaseAQAPage {
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
        PageFactory.initElements(this.getCurrentDriver(), this);
    }

    public void fillCourseSearchForm(String country, String language, String type, String[] courses, String startDate, String endDate) {
        WebDriver currentDriver = this.getCurrentDriver();
        WebDriverWait currentWait = this.getCurrentWait();
        System.out.println("Selecting course with parameters: country=" + country + ", language=" + language + ", type=" + type + ", courses=" + String.join(", ", courses) + ", startDate=" + startDate + ", endDate=" + endDate);
        WebElement countryElement = currentWait.until(ExpectedConditions.visibilityOf(countrySelect));
        new Select(countryElement).selectByVisibleText(country);

        WebElement languageElement = currentWait.until(ExpectedConditions.visibilityOf(languageSelect));
        new Select(languageElement).selectByVisibleText(language);

        WebElement typeElement = currentWait.until(ExpectedConditions.visibilityOf(typeSelect));
        new Select(typeElement).selectByVisibleText(type);

        WebElement courseElement = currentWait.until(ExpectedConditions.visibilityOf(courseMultiSelect));
        Select multiSelect = new Select(courseElement);
        for (String course : courses) {
            multiSelect.selectByVisibleText(course);
        }
        WebElement fromDateElement = currentWait.until(ExpectedConditions.visibilityOf(fromDate));
        fromDateElement.sendKeys(startDate);
        WebElement toDateElement = currentWait.until(ExpectedConditions.visibilityOf(toDate));
        toDateElement.sendKeys(endDate);
        WebElement searchButtonElement = currentWait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButtonElement.click();
    }

    public boolean isNoCoursesMessageDisplayed() {
        WebDriverWait currentWait = this.getCurrentWait();
        try {
            WebElement message = currentWait.until(ExpectedConditions.visibilityOf(noCoursesMessage));
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