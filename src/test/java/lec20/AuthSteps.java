package lec20;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lec18.test10.LoginPage2;
import lec18.test10.RegistrationPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthSteps {
    private WebDriver driver;
    private LoginPage2 loginPage;
    private RegistrationPage registrationPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        loginPage = new LoginPage2(driver);
        registrationPage = new RegistrationPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the login page")
    public void iAmOnLoginPage() {
        loginPage.open();
    }

    @When("I enter a valid email {string} and password {string}")
    public void iEnterValidEmailAndPassword(String email, String password) {
        loginPage.login(email, password);
    }

    @And("I press the login button")
    public void iClickLoginButton() {
    }

    @Then("I see the user dashboard")
    public void iSeeUserDashboard() {
        assertTrue(loginPage.isDashboardDisplayed(), "The dashboard should be displayed");
    }

    @Given("I am on the registration page")
    public void iAmOnRegistrationPage() {
        registrationPage.open();
    }

    @When("I enter registration details:")
    public void iEnterRegistrationData(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> row = data.get(0);
        registrationPage.register(
                row.get("FirstName"),
                row.get("LastName"),
                row.get("DateOfBirth"),
                row.get("Email"),
                row.get("Password"),
                row.get("ConfirmPassword")
        );
    }

    @And("I press the register button")
    public void iClickRegisterButton() {
        // Button is already clicked in register(), so this step is empty
    }

    @Then("I see an error message {string}")
    public void iSeeErrorMessage(String errorMessage) {
        switch (errorMessage) {
            case "Minimum 8 characters":
                assertTrue(registrationPage.getErrorMessageMin(), "Expected error about minimum password length");
                break;
            case "Passwords must match":
                assertTrue(registrationPage.getErrorMessagePmm(), "Expected error about mismatched passwords");
                break;
            default:
                throw new IllegalArgumentException("Unknown error message: " + errorMessage);
        }
    }
}