package lec18.test10;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthTests {
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
        driver = new ChromeDriver();
        loginPage = new LoginPage2(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void testLoginWithValidCredentials() { // TC01
        loginPage.open();
        loginPage.login("e@e", "123123123");
        assertTrue(loginPage.isDashboardDisplayed(), "User should be redirected to dashboard");
    }

    @Test
    public void testLoginWithInvalidCredentials() { // TC02
        loginPage.open();
        loginPage.login("invalid@gmail.com", "Invalid@123");

        assertTrue(loginPage.getErrorMessageLogin());
    }

    @Test
    public void testRegistrationPageLoads() { // TC03
        registrationPage.open();
        assertTrue(registrationPage.isPageLoaded(), "Registration page should load successfully");
    }

    @Test
    public void testRegistrationWithValidData() { // TC04
        String randomString = generateRandomString();
        registrationPage.open();
        registrationPage.register("John" + randomString, "Doe", "01.01.2001", "john@" + randomString + ".com", "123123123", "123123123");
        assertTrue(loginPage.isDashboardDisplayed(), "User should be redirected to login page");
    }

    @Test
    public void testRegistrationWithShortPassword() { // TC05
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "john@example.com", "Pass", "Pass");
        assertTrue(registrationPage.getErrorMessageMin());
    }

    @Test
    public void testRegistrationWithLongPassword() { // TC06
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "john@example.com",
                "Password1233456789000000000000000", "Password1233456789000000000000000");
        assertTrue(registrationPage.getErrorMessageMax());
    }

    @Test
    public void testRegistrationWithEmptyName() { // TC07
        registrationPage.open();
        registrationPage.register("", "Doe", "01.01.2001", "john@example.com", "Password123", "Password123");
        assertTrue(registrationPage.getErrorMessageReq());
    }

    @Test
    public void testRegistrationWithInvalidEmail() { // TC08
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "johnexample.com", "Password123", "Password123");
        assertTrue(registrationPage.getErrorMessageEmail());
    }

    @Test
    public void testRegistrationWithPasswordMismatch() { // TC09
        registrationPage.open();
        registrationPage.register("John", "Doe", "01.01.2001", "john@example.com", "Password123", "Password321");
        assertTrue(registrationPage.getErrorMessagePmm());
    }

    @Test
    public void testRegistrationWithEmptyLastName() { // TC07
        registrationPage.open();
        registrationPage.register("John", "", "01.01.2001", "john@example.com", "Password123", "Password123");
        assertTrue(registrationPage.getErrorMessageReq());
    }
}