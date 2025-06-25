package main.java.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;

public class CheckoutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By itemTotalLabel = By.cssSelector("[data-test='subtotal-label']");
    private By taxLabel = By.className("summary_tax_label");
    private By totalLabel = By.className("summary_total_label");
    private By finishButton = By.id("finish");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterFirstName(String firstName) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(firstNameField));
        el.click(); // focus
        el.clear();
        el.sendKeys(firstName);
    }
    
    public void enterLastName(String lastName) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(lastNameField));
        el.click(); // focus
        el.clear();
        el.sendKeys(lastName);
    }
    
    public void enterPostalCode(String postalCode) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(postalCodeField));
        el.click(); // focus
        el.clear();
        el.sendKeys(postalCode);
    }

    // This method use for scrolling to the continue button before clicking
    public void clickContinue() {
        WebElement continueElement = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueElement.click();
    }

    public String getItemTotal() {
        WebElement itemTotalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(itemTotalLabel));
        return itemTotalElement.getText();
    }

    public String getTax() {
        WebElement taxElement = wait.until(ExpectedConditions.visibilityOfElementLocated(taxLabel));
        return taxElement.getText();
    }

    public String getTotal() {
        WebElement totalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(totalLabel));
        return totalElement.getText();
    }

    public void clickFinish() {
        WebElement finishElement = wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        finishElement.click();
    }
}