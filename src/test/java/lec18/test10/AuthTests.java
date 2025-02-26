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

@Epic("Testing Authentication and Registration")
@Feature("Functional Tests")
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
        logger.info("Initializing WebDriver and pages");
        driver = new ChromeDriver();
        loginPage = new LoginPage2(driver);
        registrationPage = new RegistrationPage(driver);
        logger.info("Setup completed");
    }

    @AfterAll
    public static void tearDown() {
        logger.info("Closing WebDriver");
        if (driver != null) {
            driver.quit();
        }
        logger.info("WebDriver closed");
    }

    @Test
    @Story("Login with valid credentials")
    @Description("Checking successful login with correct email and password")
    public void testLoginWithValidCredentials() { // TC01
        logger.info("Starting test TC01: Login with valid credentials");
        loginPage.open();
        loginPage.login("e@e", "123123123");
        assertTrue(loginPage.isDashboardDisplayed(), "User should be redirected to dashboard");
        logger.info("Test TC01 completed successfully");
    }

    @Test
    @Story("Login with invalid credentials")
    @Description("Checking error display when logging in with incorrect credentials")
    public void testLoginWithInvalidCredentials() { // TC02
        logger.info("Starting test TC02: Login with invalid credentials");
        loginPage.open();
        loginPage.login("invalid@gmail.com", "Invalid@123");
        assertTrue(loginPage.getErrorMessageLogin());
        logger.info("Test TC02 completed successfully");
    }

    @Test
    @Story("Loading the registration page")
    @Description("Checking successful loading of the registration page")
    public void testRegistrationPageLoads() { // TC03
        logger.info("Starting test TC03: Loading the registration page");
        registrationPage.open();
        assertTrue(registrationPage.isPageLoaded(), "Registration page should load successfully");
        logger.info("Test TC03 completed successfully");
    }

    @Test
    @Story("Registration with valid data")
    @Description("Checking successful registration with correct data")
    public void testRegistrationWithValidData() { // TC04
        logger.info("Starting test TC04: Registration with valid data");
        String randomString = generateRandomString();
        registrationPage.open();
        registrationPage.register("John" + randomString, "Doe", "01.01.2001", "john@" + randomString + ".com", "123123123", "123123123");
        assertTrue(loginPage.isDashboardDisplayed(), "User should be redirected to login page");
        logger.info("Test TC04 completed successfully");
    }

    @Test
    @Story("Registration with a short password")
    @Description("Checking error when registering with a password shorter than 8 characters")
    public void testRegistrationWithShortPassword() { // TC05
        logger.info("Starting test TC05: Registration with a short password");
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "john@example.com", "Pass", "Pass");
        assertTrue(registrationPage.getErrorMessageMin());
        logger.info("Test TC05 completed successfully");
    }

    @Test
    @Story("Registration with a long password")
    @Description("Checking error when registering with a password longer than 20 characters")
    public void testRegistrationWithLongPassword() { // TC06
        logger.info("Starting test TC06: Registration with a long password");
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "john@example.com",
                "Password1233456789000000000000000", "Password1233456789000000000000000");
        assertTrue(registrationPage.getErrorMessageMax());
        logger.info("Test TC06 completed successfully");
    }

    @Test
    @Story("Registration with an empty name")
    @Description("Checking error when registering with an empty name field")
    public void testRegistrationWithEmptyName() { // TC07
        logger.info("Starting test TC07: Registration with an empty name");
        registrationPage.open();
        registrationPage.register("", "Doe", "01.01.2001", "john@example.com", "Password123", "Password123");
        assertTrue(registrationPage.getErrorMessageReq());
        logger.info("Test TC07 completed successfully");
    }

    @Test
    @Story("Registration with an invalid email")
    @Description("Checking error when registering with an incorrect email format")
    public void testRegistrationWithInvalidEmail() { // TC08
        logger.info("Starting test TC08: Registration with an invalid email");
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "johnexample.com", "Password123", "Password123");
        assertTrue(registrationPage.getErrorMessageEmail());
        logger.info("Test TC08 completed successfully");
    }

    @Test
    @Story("Registration with mismatched passwords")
    @Description("Checking error when registering with passwords that do not match")
    public void testRegistrationWithPasswordMismatch() { // TC09
        logger.info("Starting test TC09: Registration with mismatched passwords");
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "john@example.com", "Password123", "Password321");
        assertTrue(registrationPage.getErrorMessagePmm());
        logger.info("Test TC09 completed successfully");
    }

    @Test
    @Story("Registration with an empty last name")
    @Description("Checking error when registering with an empty last name field")
    public void testRegistrationWithEmptyLastName() { // TC10
        logger.info("Starting test: Registration with an empty last name");
        registrationPage.open();
        registrationPage.register("John", "", "01.01.2001", "john@example.com", "Password123", "Password123");
        assertTrue(registrationPage.getErrorMessageReq());
        logger.info("Test with an empty last name completed successfully");
    }
}