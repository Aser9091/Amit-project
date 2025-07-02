package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ProductPage {
    WebDriver driver;
    public ProductPage(WebDriver driver) { this.driver = driver; }

    private void handleAlerts() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert present, continue
        }
    }

    public void selectProductByIndex(int index) {
        List<WebElement> products = null;
        
        // Try to find products, handle alert if it appears
        try {
            products = driver.findElements(By.cssSelector(".card-title a"));
        } catch (UnhandledAlertException e) {
            // Alert is present, accept it and try again
            try {
                driver.switchTo().alert().accept();
                // Wait a moment for the page to settle
                Thread.sleep(1000);
                products = driver.findElements(By.cssSelector(".card-title a"));
            } catch (Exception ex) {
                System.out.println("Failed to handle alert: " + ex.getMessage());
                return;
            }
        }
        
        if (products != null && products.size() > 0) {
            System.out.println("Found " + products.size() + " products on the page");
            if (index < products.size()) {
                // Try to click the product, handle stale element if it occurs
                try {
                    String productName = products.get(index).getText();
                    System.out.println("Attempting to click product: " + productName);
                    products.get(index).click();
                } catch (StaleElementReferenceException e) {
                    // Element became stale, re-find and try again
                    try {
                        Thread.sleep(1000); // Wait for page to stabilize
                        products = driver.findElements(By.cssSelector(".card-title a"));
                        if (products != null && index < products.size()) {
                            String productName = products.get(index).getText();
                            System.out.println("Re-finding and clicking product: " + productName);
                            products.get(index).click();
                        } else {
                            System.out.println("Product index " + index + " is out of bounds after re-finding. Available products: " + (products != null ? products.size() : 0));
                        }
                    } catch (Exception ex) {
                        System.out.println("Failed to re-find and click product: " + ex.getMessage());
                    }
                }
            } else {
                System.out.println("Product index " + index + " is out of bounds. Available products: " + products.size());
            }
        } else {
            System.out.println("No products found on the page. Current URL: " + driver.getCurrentUrl());
            // Let's see what's actually on the page
            try {
                List<WebElement> allCards = driver.findElements(By.cssSelector(".card"));
                System.out.println("Found " + allCards.size() + " cards on the page");
                
                List<WebElement> allLinks = driver.findElements(By.tagName("a"));
                System.out.println("Found " + allLinks.size() + " links on the page");
                
                // Print first few links to see what's available
                for (int i = 0; i < Math.min(10, allLinks.size()); i++) {
                    try {
                        String linkText = allLinks.get(i).getText().trim();
                        if (!linkText.isEmpty()) {
                            System.out.println("Link " + i + ": '" + linkText + "'");
                        }
                    } catch (Exception e) {
                        // Skip stale elements
                    }
                }
            } catch (Exception e) {
                System.out.println("Error analyzing page content: " + e.getMessage());
            }
        }
    }
    
    public void addToCart() {
        handleAlerts(); // Handle any open alerts first
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Add to cart")));
            System.out.println("Clicking 'Add to cart' button");
            addToCartButton.click();
        } catch (Exception e) {
            System.out.println("Error clicking Add to cart: " + e.getMessage());
        }
    }
    
    public boolean isAddToCartSuccess() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            System.out.println("Add to cart alert: " + alertText);
            driver.switchTo().alert().accept();
            return alertText.contains("Product added");
        } catch (Exception e) {
            System.out.println("No add to cart alert found: " + e.getMessage());
            return false;
        }
    }
} 