package lesson3;

import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTestWithParameters extends LoginTest {
    @Test(groups = "ParamTests")
    @Parameters({"username", "password"})
    public void testValidLoginWithParams(String username, String password) {
        setUp();
        login(username, password);
        signOutElement = wait.until(ExpectedConditions.visibilityOfElementLocated(signOutLocator));
        Assert.assertTrue(signOutElement.isDisplayed());
        tearDown();
    }

}
