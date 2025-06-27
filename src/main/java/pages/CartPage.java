package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage {
    private WebDriver driver;

    // Locators
    private By cartItems = By.className("cart_item");
    private By itemName = By.className("inventory_item_name");
    private By itemQuantity = By.className("cart_quantity");
    private By removeButton = By.xpath(".//button[contains(text(),'Remove')]");
    private By checkoutButton = By.id("checkout");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<WebElement> getCartItems() {
        return driver.findElements(cartItems);
    }

    public String getItemName(WebElement cartItem) {
        return cartItem.findElement(itemName).getText();
    }

    public int getItemQuantity(WebElement cartItem) {
        String qtyText = cartItem.findElement(itemQuantity).getText();
        // Handle cases where the quantity might not be a valid integer
        try {
            return Integer.parseInt(qtyText);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void removeItem(WebElement cartItem) {
        cartItem.findElement(removeButton).click();
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }
}
