package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    public LoginPage(WebDriver driver) { this.driver = driver; }

    private void handleAlerts() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
        } catch (Exception e) {
            // No alert present, continue
        }
    }

    public void enterUsername(String username) {
        handleAlerts(); // Handle any open alerts first
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("loginusername")));
            driver.findElement(By.id("loginusername")).clear();
            driver.findElement(By.id("loginusername")).sendKeys(username);
        } catch (Exception e) {
            System.out.println("Could not enter username: " + e.getMessage());
        }
    }

    public void enterPassword(String password) {
        handleAlerts(); // Handle any open alerts first
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("loginpassword")));
            driver.findElement(By.id("loginpassword")).clear();
            driver.findElement(By.id("loginpassword")).sendKeys(password);
        } catch (Exception e) {
            System.out.println("Could not enter password: " + e.getMessage());
        }
    }

    public void clickLoginButton() {
        handleAlerts(); // Handle any open alerts first
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Log in')]")));
            driver.findElement(By.xpath("//button[contains(text(),'Log in')]")).click();
        } catch (Exception e) {
            System.out.println("Could not click login button: " + e.getMessage());
        }
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
    
    public String getAlertText() {
        try {
            // First check if alert is present without waiting
            try {
                return driver.switchTo().alert().getText();
            } catch (Exception e) {
                // If no alert present, wait for one
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.alertIsPresent());
                return driver.switchTo().alert().getText();
            }
        } catch (Exception e) {
            System.out.println("No alert present: " + e.getMessage());
            return "";
        }
    }
    
    public void acceptAlert() {
        try {
            // First check if alert is present without waiting
            try {
                driver.switchTo().alert().accept();
            } catch (Exception e) {
                // If no alert present, wait for one
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.alertIsPresent());
                driver.switchTo().alert().accept();
            }
        } catch (Exception e) {
            System.out.println("No alert to accept: " + e.getMessage());
        }
    }
    
    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 