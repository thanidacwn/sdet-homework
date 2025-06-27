package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By productSortDropdown = By.className("product_sort_container");
    private By inventoryItems = By.className("inventory_item");
    private By inventoryItemName = By.className("inventory_item_name");
    private By inventoryItemPrice = By.className("inventory_item_price");
    private By shoppingCartBadge = By.className("shopping_cart_badge");
    private By shoppingCartLink = By.className("shopping_cart_link");

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void sortByPriceHighToLow() {
        WebElement dropdown = driver.findElement(productSortDropdown);
        dropdown.click();
        dropdown.findElement(By.xpath("//option[@value='hilo']")).click();
    }

    public List<WebElement> getInventoryItems() {
        return driver.findElements(inventoryItems);
    }

    // Add product to cart by index in the inventory list
    public void addProductToCartByIndex(int index) {
        List<WebElement> items = getInventoryItems();
        if (index >= 0 && index < items.size()) {
            WebElement item = items.get(index);
            WebElement addButton = item.findElement(By.xpath(".//button[contains(text(),'Add to cart')]"));

            wait.until(ExpectedConditions.elementToBeClickable(addButton));
            addButton.click();

            // wait for the shopping cart badge to update
            wait.until(driver -> {
                try {
                    WebElement badge = driver.findElement(shoppingCartBadge);
                    String countText = badge.getText();
                    int count = Integer.parseInt(countText);
                    // if count >= 1, it means the product was added successfully
                    return count >= 1;
                } catch (Exception e) {
                    return false;
                }
            });
        }
    }

    // Navigate to the shopping cart page
    public void goToCart() {
        driver.findElement(shoppingCartLink).click();
    }

    // Get the number of items in the cart to verify if products were added successfully
    public int getCartItemCount() {
        try {
            String countText = driver.findElement(By.className("shopping_cart_badge")).getText();
            return Integer.parseInt(countText);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return 0; // if badge is not present, it means cart is empty
        }
    }

    // Get product name and price by index in the inventory list for verification
    public String getProductNameByIndex(int index) {
        List<WebElement> items = getInventoryItems();
        if (index >= 0 && index < items.size()) {
            return items.get(index).findElement(inventoryItemName).getText();
        }
        return "";
    }

    // Get product price by index in the inventory list for verification
    public String getProductPriceByIndex(int index) {
        List<WebElement> items = getInventoryItems();
        if (index >= 0 && index < items.size()) {
            return items.get(index).findElement(inventoryItemPrice).getText();
        }
        return "";
    }
}
