package lec18.test10;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class RegistrationPage {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationPage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(name = "firstName")
    private WebElement firstNameField;

    @FindBy(name = "lastName")
    private WebElement lastNameField;

    @FindBy(name = "dateOfBirth")
    private WebElement dobField;

    @FindBy(name = "email")
    private WebElement emailField;

    @FindBy(name = "password")
    private WebElement passwordField;

    @FindBy(name = "passwordConfirmation")
    private WebElement confirmPasswordField;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    @FindBy(xpath = "//*[contains(text(), 'Sign Out')]")
    private WebElement signOut;

    @FindBy(xpath = "//*[contains(text(), 'Required')]")
    private WebElement errorMessageReq;

    @FindBy(xpath = "//*[contains(text(), 'Minimum 8 characters')]")
    private WebElement errorMessageMin;

    @FindBy(xpath = "//*[contains(text(), 'Maximum 20 characters')]")
    private WebElement errorMessageMax;

    @FindBy(xpath = "//*[contains(text(), 'Invalid email address')]")
    private WebElement errorMessageEmail;

    @FindBy(xpath = "//*[contains(text(), 'Passwords must match')]")
    private WebElement errorMessagePmm;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @Step("Открытие страницы регистрации")
    public void open() {
        logger.info("Открываем страницу регистрации");
        driver.get("https://qa-course-01.andersenlab.com/registration");
        logger.info("Страница регистрации открыта");
    }

    @Step("Регистрация с данными: имя={firstName}, фамилия={lastName}, дата рождения={dob}, email={email}, пароль={password}, подтверждение пароля={confirmPassword}")
    public void register(String firstName, String lastName, String dob, String email, String password, String confirmPassword) {
        logger.info("Вводим имя: {}", firstName);
        firstNameField.sendKeys(firstName);
        logger.info("Вводим фамилию: {}", lastName);
        lastNameField.sendKeys(lastName);
        logger.info("Вводим дату рождения: {}", dob);
        dobField.sendKeys(dob);
        dobField.sendKeys(Keys.ESCAPE);
        logger.info("Вводим email: {}", email);
        emailField.sendKeys(email);
        logger.info("Вводим пароль: {}", password);
        passwordField.sendKeys(password);
        logger.info("Вводим подтверждение пароля: {}", confirmPassword);
        confirmPasswordField.sendKeys(confirmPassword);
        logger.info("Нажимаем кнопку Submit");
        submitButton.click();
    }

    @Step("Проверка отображения страницы логина")
    public boolean isLoginPageDisplayed() {
        logger.info("Проверяем переход на страницу логина");
        boolean isDisplayed = driver.getCurrentUrl().contains("login");
        logger.info("Страница логина отображена: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Проверка отображения кнопки Sign Out")
    public boolean getErrorMessageSo() {
        logger.info("Ожидаем отображения кнопки Sign Out");
        wait.until(ExpectedConditions.visibilityOf(signOut));
        boolean isDisplayed = signOut.isDisplayed();
        logger.info("Кнопка Sign Out отображена: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Проверка ошибки 'Minimum 8 characters'")
    public boolean getErrorMessageMin() {
        logger.info("Ожидаем отображения ошибки 'Minimum 8 characters'");
        wait.until(ExpectedConditions.visibilityOf(errorMessageMin));
        boolean isDisplayed = errorMessageMin.isDisplayed();
        logger.info("Ошибка 'Minimum 8 characters' отображена: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Проверка ошибки 'Required'")
    public boolean getErrorMessageReq() {
        logger.info("Ожидаем отображения ошибки 'Required'");
        wait.until(ExpectedConditions.visibilityOf(errorMessageReq));
        boolean isDisplayed = errorMessageReq.isDisplayed();
        logger.info("Ошибка 'Required' отображена: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Проверка ошибки 'Maximum 20 characters'")
    public boolean getErrorMessageMax() {
        logger.info("Ожидаем отображения ошибки 'Maximum 20 characters'");
        wait.until(ExpectedConditions.visibilityOf(errorMessageMax));
        boolean isDisplayed = errorMessageMax.isDisplayed();
        logger.info("Ошибка 'Maximum 20 characters' отображена: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Проверка ошибки 'Invalid email address'")
    public boolean getErrorMessageEmail() {
        logger.info("Ожидаем отображения ошибки 'Invalid email address'");
        wait.until(ExpectedConditions.visibilityOf(errorMessageEmail));
        boolean isDisplayed = errorMessageEmail.isDisplayed();
        logger.info("Ошибка 'Invalid email address' отображена: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Проверка ошибки 'Passwords must match'")
    public boolean getErrorMessagePmm() {
        logger.info("Ожидаем отображения ошибки 'Passwords must match'");
        wait.until(ExpectedConditions.visibilityOf(errorMessagePmm));
        boolean isDisplayed = errorMessagePmm.isDisplayed();
        logger.info("Ошибка 'Passwords must match' отображена: {}", isDisplayed);
        return isDisplayed;
    }

    @Step("Проверка загрузки страницы регистрации")
    public boolean isPageLoaded() {
        logger.info("Проверяем загрузку страницы регистрации");
        boolean isLoaded = driver.getCurrentUrl().contains("registration");
        logger.info("Страница регистрации загружена: {}", isLoaded);
        return isLoaded;
    }
}