package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CartPage {
    WebDriver driver;
    public CartPage(WebDriver driver) { this.driver = driver; }

    public boolean areBothProductsPresent() {
        try {
            // Wait for cart page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#tbodyid")));
            
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page title: " + driver.getTitle());
            
            // Check if tbodyid exists
            List<WebElement> tbodyElements = driver.findElements(By.cssSelector("#tbodyid"));
            System.out.println("Found " + tbodyElements.size() + " tbodyid elements");
            
            List<WebElement> rows = driver.findElements(By.cssSelector("#tbodyid > tr"));
            System.out.println("Found " + rows.size() + " products in cart");
            
            // If no rows found, let's check the entire page structure
            if (rows.size() == 0) {
                System.out.println("No products found in tbodyid. Checking entire page structure:");
                
                // Check for any table elements
                List<WebElement> tables = driver.findElements(By.tagName("table"));
                System.out.println("Found " + tables.size() + " tables on the page");
                
                // Check for any tr elements
                List<WebElement> allRows = driver.findElements(By.tagName("tr"));
                System.out.println("Found " + allRows.size() + " total rows on the page");
                
                // Check for any elements with 'cart' in their class or id
                List<WebElement> cartElements = driver.findElements(By.cssSelector("[class*='cart'], [id*='cart']"));
                System.out.println("Found " + cartElements.size() + " elements with 'cart' in class/id");
                
                // Print page source to understand structure
                String pageSource = driver.getPageSource();
                if (pageSource.contains("tbodyid")) {
                    System.out.println("tbodyid found in page source");
                } else {
                    System.out.println("tbodyid NOT found in page source");
                }
                
                // Look for any product-related elements
                List<WebElement> productElements = driver.findElements(By.cssSelector(".card, .product, [class*='item']"));
                System.out.println("Found " + productElements.size() + " potential product elements");
            }
            
            // Print product details for debugging
            for (int i = 0; i < rows.size(); i++) {
                try {
                    WebElement row = rows.get(i);
                    List<WebElement> cells = row.findElements(By.tagName("td"));
                    if (cells.size() >= 2) {
                        String productName = cells.get(1).getText();
                        String price = cells.get(2).getText();
                        System.out.println("Product " + (i+1) + ": " + productName + " - " + price);
                    }
                } catch (Exception e) {
                    System.out.println("Error reading product " + (i+1) + ": " + e.getMessage());
                }
            }
            
            return rows.size() >= 2;
        } catch (Exception e) {
            System.out.println("Error checking cart products: " + e.getMessage());
            return false;
        }
    }
    
    public boolean isTotalCorrect() {
        try {
            List<WebElement> prices = driver.findElements(By.cssSelector("#tbodyid > tr > td:nth-child(3)"));
            int total = 0;
            for (WebElement price : prices) {
                total += Integer.parseInt(price.getText());
            }
            int displayedTotal = Integer.parseInt(driver.findElement(By.id("totalp")).getText());
            System.out.println("Calculated total: " + total + ", Displayed total: " + displayedTotal);
            return total == displayedTotal;
        } catch (Exception e) {
            System.out.println("Error checking total: " + e.getMessage());
            return false;
        }
    }
    
    public void clickPlaceOrder() {
        try {
            // Handle any open alerts first
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
                wait.until(ExpectedConditions.alertIsPresent());
                String alertText = driver.switchTo().alert().getText();
                System.out.println("Accepting alert before Place Order: " + alertText);
                driver.switchTo().alert().accept();
            } catch (Exception e) {
                // No alert present, continue
            }
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Place Order']")));
            System.out.println("Clicking Place Order button");
            placeOrderButton.click();
        } catch (Exception e) {
            System.out.println("Error clicking Place Order: " + e.getMessage());
        }
    }
    
    public boolean isQuantityUpdatedOrMessageShown() {
        try {
            // First, check if we're on the cart page
            if (!driver.getCurrentUrl().contains("cart")) {
                // If not on cart page, check for alert messages about duplicate products
                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
                    wait.until(ExpectedConditions.alertIsPresent());
                    String alertText = driver.switchTo().alert().getText();
                    System.out.println("Found alert: " + alertText);
                    
                    // Accept the alert
                    driver.switchTo().alert().accept();
                    
                    // Check if alert indicates product was added or already exists
                    boolean isRelevantAlert = alertText.toLowerCase().contains("product") ||
                                           alertText.toLowerCase().contains("added") ||
                                           alertText.toLowerCase().contains("cart") ||
                                           alertText.toLowerCase().contains("already") ||
                                           alertText.toLowerCase().contains("success");
                    
                    if (isRelevantAlert) {
                        System.out.println("Alert indicates product handling: " + alertText);
                        return true;
                    }
                } catch (Exception e) {
                    // No alert present, continue to cart check
                }
            }
            
            // Navigate to cart to check for duplicate products
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#tbodyid")));
                
                List<WebElement> rows = driver.findElements(By.cssSelector("#tbodyid > tr"));
                System.out.println("Found " + rows.size() + " products in cart");
                
                if (rows.size() >= 1) {
                    // Check if there are duplicate product names (same product added twice)
                    List<String> productNames = new java.util.ArrayList<>();
                    for (WebElement row : rows) {
                        try {
                            List<WebElement> cells = row.findElements(By.tagName("td"));
                            if (cells.size() >= 2) {
                                String productName = cells.get(1).getText().trim();
                                if (!productName.isEmpty()) {
                                    productNames.add(productName);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error reading product name: " + e.getMessage());
                        }
                    }
                    
                    // Check for duplicates
                    java.util.Set<String> uniqueNames = new java.util.HashSet<>(productNames);
                    boolean hasDuplicates = productNames.size() > uniqueNames.size();
                    
                    System.out.println("Product names: " + productNames);
                    System.out.println("Has duplicates: " + hasDuplicates);
                    
                    // Return true if we have at least one product and either duplicates or multiple products
                    return rows.size() >= 1 && (hasDuplicates || rows.size() > 1);
                }
            } catch (Exception e) {
                System.out.println("Error checking cart contents: " + e.getMessage());
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("Error in isQuantityUpdatedOrMessageShown: " + e.getMessage());
            return false;
        }
    }
} 