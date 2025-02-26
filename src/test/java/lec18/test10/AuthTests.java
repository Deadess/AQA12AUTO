package lec18.test10;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Тестирование авторизации и регистрации")
@Feature("Функциональные тесты")
public class AuthTests {
    private static final Logger logger = LoggerFactory.getLogger(AuthTests.class);
    private static WebDriver driver;
    private static LoginPage2 loginPage;
    private static RegistrationPage registrationPage;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new Random();

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(3);
        for (int i = 0; i < 3; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    @BeforeAll
    public static void setUp() {
        logger.info("Инициализация WebDriver и страниц");
        driver = new ChromeDriver();
        loginPage = new LoginPage2(driver);
        registrationPage = new RegistrationPage(driver);
        logger.info("Настройка завершена");
    }

    @AfterAll
    public static void tearDown() {
        logger.info("Закрытие WebDriver");
        if (driver != null) {
            driver.quit();
        }
        logger.info("WebDriver закрыт");
    }

    @Test
    @Story("Логин с валидными данными")
    @Description("Проверка успешного входа с корректными email и паролем")
    public void testLoginWithValidCredentials() { // TC01
        logger.info("Запуск теста TC01: Логин с валидными данными");
        loginPage.open();
        loginPage.login("e@e", "123123123");
        assertTrue(loginPage.isDashboardDisplayed(), "User should be redirected to dashboard");
        logger.info("Тест TC01 успешно завершен");
    }

    @Test
    @Story("Логин с невалидными данными")
    @Description("Проверка отображения ошибки при входе с некорректными данными")
    public void testLoginWithInvalidCredentials() { // TC02
        logger.info("Запуск теста TC02: Логин с невалидными данными");
        loginPage.open();
        loginPage.login("invalid@gmail.com", "Invalid@123");
        assertTrue(loginPage.getErrorMessageLogin());
        logger.info("Тест TC02 успешно завершен");
    }

    @Test
    @Story("Загрузка страницы регистрации")
    @Description("Проверка успешной загрузки страницы регистрации")
    public void testRegistrationPageLoads() { // TC03
        logger.info("Запуск теста TC03: Загрузка страницы регистрации");
        registrationPage.open();
        assertTrue(registrationPage.isPageLoaded(), "Registration page should load successfully");
        logger.info("Тест TC03 успешно завершен");
    }

    @Test
    @Story("Регистрация с валидными данными")
    @Description("Проверка успешной регистрации с корректными данными")
    public void testRegistrationWithValidData() { // TC04
        logger.info("Запуск теста TC04: Регистрация с валидными данными");
        String randomString = generateRandomString();
        registrationPage.open();
        registrationPage.register("John" + randomString, "Doe", "01.01.2001", "john@" + randomString + ".com", "123123123", "123123123");
        assertTrue(loginPage.isDashboardDisplayed(), "User should be redirected to login page");
        logger.info("Тест TC04 успешно завершен");
    }

    @Test
    @Story("Регистрация с коротким паролем")
    @Description("Проверка ошибки при регистрации с паролем короче 8 символов")
    public void testRegistrationWithShortPassword() { // TC05
        logger.info("Запуск теста TC05: Регистрация с коротким паролем");
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "john@example.com", "Pass", "Pass");
        assertTrue(registrationPage.getErrorMessageMin());
        logger.info("Тест TC05 успешно завершен");
    }

    @Test
    @Story("Регистрация с длинным паролем")
    @Description("Проверка ошибки при регистрации с паролем длиннее 20 символов")
    public void testRegistrationWithLongPassword() { // TC06
        logger.info("Запуск теста TC06: Регистрация с длинным паролем");
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "john@example.com",
                "Password1233456789000000000000000", "Password1233456789000000000000000");
        assertTrue(registrationPage.getErrorMessageMax());
        logger.info("Тест TC06 успешно завершен");
    }

    @Test
    @Story("Регистрация с пустым именем")
    @Description("Проверка ошибки при регистрации с пустым полем имени")
    public void testRegistrationWithEmptyName() { // TC07
        logger.info("Запуск теста TC07: Регистрация с пустым именем");
        registrationPage.open();
        registrationPage.register("", "Doe", "01.01.2001", "john@example.com", "Password123", "Password123");
        assertTrue(registrationPage.getErrorMessageReq());
        logger.info("Тест TC07 успешно завершен");
    }

    @Test
    @Story("Регистрация с невалидным email")
    @Description("Проверка ошибки при регистрации с некорректным форматом email")
    public void testRegistrationWithInvalidEmail() { // TC08
        logger.info("Запуск теста TC08: Регистрация с невалидным email");
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "johnexample.com", "Password123", "Password123");
        assertTrue(registrationPage.getErrorMessageEmail());
        logger.info("Тест TC08 успешно завершен");
    }

    @Test
    @Story("Регистрация с несовпадающими паролями")
    @Description("Проверка ошибки при регистрации с паролями, которые не совпадают")
    public void testRegistrationWithPasswordMismatch() { // TC09
        logger.info("Запуск теста TC09: Регистрация с несовпадающими паролями");
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "john@example.com", "Password123", "Password321");
        assertTrue(registrationPage.getErrorMessagePmm());
        logger.info("Тест TC09 успешно завершен");
    }

    @Test
    @Story("Регистрация с пустой фамилией")
    @Description("Проверка ошибки при регистрации с пустым полем фамилии")
    public void testRegistrationWithEmptyLastName() { // TC10
        logger.info("Запуск теста: Регистрация с пустой фамилией");
        registrationPage.open();
        registrationPage.register("John", "", "01.01.2001", "john@example.com", "Password123", "Password123");
        assertTrue(registrationPage.getErrorMessageReq());
        logger.info("Тест с пустой фамилией успешно завершен");
    }
}