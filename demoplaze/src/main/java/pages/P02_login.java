package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class P02_login {
    public WebDriver driver;

    public P02_login(WebDriver driver) {
        this.driver = driver;
    }
    
    // Login link
    public WebElement loginLink() {
        return driver.findElement(By.cssSelector("a[class=\"ico-login\"]"));
    }
    
    // Email field
    public WebElement email() {
        return driver.findElement(By.id("Email"));
    }
    
    // Password field
    public WebElement password() {
        return driver.findElement(By.id("Password"));
    }
    
    // Login button
    public WebElement loginButton() {
        return driver.findElement(By.cssSelector("button[class=\"button-1 login-button\"]"));
    }
    
    // My account tab (for successful login verification)
    public WebElement myAccountTab() {
        return driver.findElement(By.cssSelector("a[class=\"ico-account\"]"));
    }
    
    // Error message (for failed login verification)
    public WebElement errorMessage() {
        return driver.findElement(By.cssSelector(".message-error"));
    }
    
    // Methods
    public void clickLoginLink() {
        loginLink().click();
    }
    
    public void enterEmail(String email) {
        this.email().sendKeys(email);
    }
    
    public void enterPassword(String password) {
        this.password().sendKeys(password);
    }
    
    public void clickLoginButton() {
        loginButton().click();
    }
} 