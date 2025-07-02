package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class HomePage {
    WebDriver driver;
    public HomePage(WebDriver driver) { this.driver = driver; }

    private void handleAlerts() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert present, continue
        }
    }

    public void clickSignUpHeader() {
        handleAlerts(); // Handle any open alerts first
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement signUpButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("signin2")));
            signUpButton.click();
        } catch (Exception e) {
            System.out.println("Error clicking sign up header: " + e.getMessage());
        }
    }
    
    public void clickLoginHeader() {
        handleAlerts(); // Handle any open alerts first
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login2")));
            loginButton.click();
        } catch (Exception e) {
            System.out.println("Error clicking login header: " + e.getMessage());
        }
    }
    
    public void clickCartHeader() {
        handleAlerts(); // Handle any open alerts first
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur")));
            System.out.println("Clicking cart header button");
            cartButton.click();
            System.out.println("Cart button clicked. Current URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Error clicking cart header: " + e.getMessage());
        }
    }
    
    public void selectCategory(String category) {
        handleAlerts(); // Handle any open alerts first
        try {
            // First, let's see what categories are available
            List<WebElement> allLinks = driver.findElements(By.tagName("a"));
            System.out.println("Available links on page:");
            for (WebElement link : allLinks) {
                try {
                    String linkText = link.getText().trim();
                    if (!linkText.isEmpty()) {
                        System.out.println("Link text: '" + linkText + "'");
                    }
                } catch (Exception e) {
                    // Skip stale elements
                }
            }
            
            // Try different selectors for category links
            WebElement categoryLink = null;
            
            // Try 1: Direct link text
            try {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                categoryLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(category)));
            } catch (Exception e) {
                System.out.println("Could not find category by link text: " + category);
            }
            
            // Try 2: Partial link text
            if (categoryLink == null) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    categoryLink = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(category)));
                } catch (Exception e) {
                    System.out.println("Could not find category by partial link text: " + category);
                }
            }
            
            // Try 3: Look for category links in specific containers
            if (categoryLink == null) {
                try {
                    List<WebElement> categoryLinks = driver.findElements(By.cssSelector(".list-group a"));
                    for (WebElement link : categoryLinks) {
                        if (link.getText().trim().equalsIgnoreCase(category)) {
                            categoryLink = link;
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Could not find category in list-group: " + category);
                }
            }
            
            if (categoryLink != null) {
                categoryLink.click();
                System.out.println("Successfully clicked category: " + category);
                // Wait for the page to load after category selection
                Thread.sleep(2000);
            } else {
                System.out.println("Category '" + category + "' not found on the page");
            }
            
        } catch (Exception e) {
            System.out.println("Error selecting category " + category + ": " + e.getMessage());
        }
    }
} 