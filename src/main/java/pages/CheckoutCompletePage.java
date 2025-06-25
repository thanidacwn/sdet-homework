package main.java.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class CheckoutCompletePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locator
    private By completeHeader = By.className("complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getCompleteMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement completeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader));
        return completeElement.getText();
    }

    // Check if the order complete message is shown and contains the expected text
    public boolean isOrderCompleteMessageShown() {
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader));
            return header.getText().contains("THANK YOU FOR YOUR ORDER");
        } catch (Exception e) {
            return false;
        }
    }
}
