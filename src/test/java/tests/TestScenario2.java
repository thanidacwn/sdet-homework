package tests;

import pages.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import static org.testng.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.HashMap;
import java.util.Map;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TestScenario2 {

    private WebDriver driver;
    private LoginPage loginPage;

    // Setup WebDriver and page objects before tests
    @BeforeClass
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setup() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    // Close WebDriver after tests
    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testLockedOutUserCannotLogin() {
        // Attempt to log in with locked out user credentials
        loginPage.loginAs("locked_out_user", "secret_sauce");
        // Wait for the error message to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']")));

        // Validate URL should not change
        String currentUrl = driver.getCurrentUrl();
        assertEquals(currentUrl, "https://www.saucedemo.com/", "User should not be logged in");

        // Validate error message
        String errorMsg = loginPage.getErrorMessage();
        System.out.println("Error message: " + errorMsg);
        assertTrue(errorMsg.contains("Sorry, this user has been locked out."), "Correct error message should be displayed");
    }
}
