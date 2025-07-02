package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CheckoutPage {
    WebDriver driver;
    public CheckoutPage(WebDriver driver) { this.driver = driver; }

    public void waitForCheckoutModal() {
        try {
            System.out.println("Waiting for checkout modal to appear...");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            
            // Wait for the modal to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            
            // Try different selectors for the checkout modal
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
                System.out.println("Checkout modal found with name field");
            } catch (Exception e) {
                System.out.println("Name field not found, trying alternative selectors");
                
                // Try other possible modal selectors
                try {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".modal.show")));
                    System.out.println("Modal found with .modal.show class");
                } catch (Exception e2) {
                    try {
                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[class*='modal']")));
                        System.out.println("Modal found with modal in class name");
                    } catch (Exception e3) {
                        System.out.println("No modal found. Checking page source for modal elements");
                        
                        // Check if there are any modal-related elements on the page
                        String pageSource = driver.getPageSource();
                        if (pageSource.contains("modal")) {
                            System.out.println("Modal found in page source");
                        } else {
                            System.out.println("No modal elements found in page source");
                        }
                        
                        throw new RuntimeException("Checkout modal not found after trying multiple selectors");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error waiting for checkout modal: " + e.getMessage());
            throw e;
        }
    }

    public void fillOrderDetails(String name, String country, String city, String card, String month, String year) {
        waitForCheckoutModal();
        driver.findElement(By.id("name")).sendKeys(name);
        driver.findElement(By.id("country")).sendKeys(country);
        driver.findElement(By.id("city")).sendKeys(city);
        driver.findElement(By.id("card")).sendKeys(card);
        driver.findElement(By.id("month")).sendKeys(month);
        driver.findElement(By.id("year")).sendKeys(year);
    }
    
    public void clickPurchase() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement purchaseButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Purchase']")));
            System.out.println("Clicking Purchase button");
            purchaseButton.click();
        } catch (Exception e) {
            System.out.println("Error clicking Purchase button: " + e.getMessage());
        }
    }
    
    public String getSuccessMessage() {
        try {
            // Wait a moment for any success message to appear
            Thread.sleep(2000);
            
            System.out.println("Looking for success message...");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            
            // Try different selectors for success messages
            String successMessage = "";
            
            // Try 1: Sweet alert
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sweet-alert.showSweetAlert.visible h2"))).getText();
                System.out.println("Found success message in sweet alert: " + successMessage);
                return successMessage;
            } catch (Exception e) {
                System.out.println("No sweet alert found: " + e.getMessage());
            }
            
            // Try 2: Regular alert
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.alertIsPresent());
                successMessage = driver.switchTo().alert().getText();
                System.out.println("Found success message in alert: " + successMessage);
                driver.switchTo().alert().accept();
                return successMessage;
            } catch (Exception e) {
                System.out.println("No alert found: " + e.getMessage());
            }
            
            // Try 3: Check page source for success text
            String pageSource = driver.getPageSource();
            if (pageSource.contains("Thank you for your purchase")) {
                System.out.println("Found 'Thank you for your purchase' in page source");
                return "Thank you for your purchase!";
            }
            
            System.out.println("No success message found");
            return "";
            
        } catch (Exception e) {
            System.out.println("Error getting success message: " + e.getMessage());
            return "";
        }
    }
    
    public String getErrorMessage() {
        try {
            System.out.println("Looking for error message...");
            
            // Try 1: Sweet alert for errors
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sweet-alert.showSweetAlert.visible h2"))).getText();
                System.out.println("Found error message in sweet alert: " + errorMessage);
                return errorMessage;
            } catch (Exception e) {
                System.out.println("No sweet alert error found: " + e.getMessage());
            }
            
            // Try 2: Regular alert
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.alertIsPresent());
                String errorMessage = driver.switchTo().alert().getText();
                System.out.println("Found error message in alert: " + errorMessage);
                driver.switchTo().alert().accept();
                return errorMessage;
            } catch (Exception e) {
                System.out.println("No alert error found: " + e.getMessage());
            }
            
            // Try 3: Check page source for error indicators
            String pageSource = driver.getPageSource();
            if (pageSource.contains("error") || pageSource.contains("invalid") || pageSource.contains("failed")) {
                System.out.println("Found error indicators in page source");
                return "Error detected";
            }
            
            System.out.println("No error message found");
            return "";
            
        } catch (Exception e) {
            System.out.println("Error getting error message: " + e.getMessage());
            return "";
        }
    }
} 