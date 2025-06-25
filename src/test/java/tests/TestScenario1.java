package test.java.tests;

import main.java.pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions; 
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.testng.annotations.*;
import static org.testng.Assert.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public class TestScenario1 {

    private WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private CheckoutCompletePage checkoutCompletePage;

    // Setup WebDriver and page objects before tests
    @BeforeClass
    public void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); // Use incognito mode
        options.addArguments("--disable-save-password-bubble"); // close save password bubble
        options.addArguments("--disable-notifications"); // close notifications to avoid popups
        options.addArguments("--disable-popup-blocking"); // close popup blocking
        options.setExperimentalOption("prefs", Map.of(
            "credentials_enable_service", false,
            "profile.password_manager_enabled", false,
            "profile.default_content_setting_values.notifications", 2
        ));
        driver = new ChromeDriver(options);
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);
    }

    // Close WebDriver after tests
    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void testScenario1_AddTwoProductsAndCheckout() {
        // Login with valid user
        loginPage.loginAs("standard_user", "secret_sauce");

        // Wait for inventory page to load and verify URL
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        
        assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Login should redirect to inventory page");
        // Print current URL for debugging
        System.out.println("Current URL after login: " + driver.getCurrentUrl());

        // Sort products by price high to low
        inventoryPage.sortByPriceHighToLow();

        // add 2 products to cart automatically
        for (int i = 0; i < 2; i++) {
            inventoryPage.addProductToCartByIndex(i);
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("shopping_cart_badge"), String.valueOf(i + 1)));
        }

        // Verify cart badge count contains 2 items
        int cartItemCount = inventoryPage.getCartItemCount();
        assertEquals(cartItemCount, 2, "Cart should contain 2 items");
        // Go to cart page
        inventoryPage.goToCart();

        // Verify items in cart: quantity, names, and remove button presence
        List<WebElement> cartItems = cartPage.getCartItems();
        assertEquals(cartItems.size(), 2, "Cart should contain 2 items");

        // check each item in the cart
        for (WebElement item : cartItems) {
            String name = cartPage.getItemName(item);
            int qty = cartPage.getItemQuantity(item);
            assertTrue(name.length() > 0, "Item name should not be empty");
            assertEquals(qty, 1, "Each item quantity should be 1");
            assertTrue(item.findElements(By.xpath(".//button[contains(text(),'Remove')]")).size() > 0, "Remove button should be present");
        }

        // Proceed to checkout
        cartPage.clickCheckout();

        // Fill in checkout information
        checkoutPage.enterFirstName("Thanida");
        checkoutPage.enterLastName("Chaiwongnon");
        checkoutPage.enterPostalCode("10900");
        checkoutPage.clickContinue();

        // Verify price total details on overview page
        String itemTotal = checkoutPage.getItemTotal();
        String tax = checkoutPage.getTax();
        String total = checkoutPage.getTotal();
        assertTrue(itemTotal.contains("Item total:"), "Item total label should be present");
        assertTrue(tax.contains("Tax:"), "Tax label should be present");
        assertTrue(total.contains("Total:"), "Total label should be present");

        // Finish order by clicking Finish button
        checkoutPage.clickFinish();
        
        // Wait for checkout complete page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));

        // Verify successful order message
        String completeMsg = checkoutCompletePage.getCompleteMessage();
        System.out.println("Complete message text: " + completeMsg);
        assertTrue(completeMsg.contains("Thank you for your order!"), "Order completion message should be shown");
    }
}
