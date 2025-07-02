package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class P01_register {
    public WebDriver driver;

    public P01_register(WebDriver driver) {
        this.driver = driver;
    }
    
    // Register link
    public WebElement registerlink() {
        // Try to find registration link with various selectors
        try {
            // First try the original selector
            return driver.findElement(By.cssSelector("a[class=\"ico-signUp\"]"));
        } catch (Exception e) {
            try {
                // Try alternative class name
                return driver.findElement(By.cssSelector("a[class=\"ico-register\"]"));
            } catch (Exception e2) {
                try {
                    // Try by link text
                    return driver.findElement(By.linkText("Register"));
                } catch (Exception e3) {
                    try {
                        // Try by partial link text
                        return driver.findElement(By.partialLinkText("Register"));
                    } catch (Exception e4) {
                        try {
                            // Try by href containing register
                            return driver.findElement(By.cssSelector("a[href*=\"register\"]"));
                        } catch (Exception e5) {
                            // If all else fails, try to find any link with "register" in the text or href
                            return driver.findElement(By.xpath("//a[contains(text(), 'Register') or contains(@href, 'register') or contains(@class, 'register')]"));
                        }
                    }
                }
            }
        }
    }
    
    // Gender elements
    public WebElement maleGender() {
        return driver.findElement(By.id("gender-male"));
    }
    
    public WebElement femaleGender() {
        return driver.findElement(By.id("gender-female"));
    }
    
    // Name fields
    public WebElement firstName() {
        return driver.findElement(By.id("FirstName"));
    }
    
    public WebElement lastName() {
        return driver.findElement(By.id("LastName"));
    }
    
    // Date of birth dropdowns
    public Select dayOfBirth() {
        return new Select(driver.findElement(By.name("DateOfBirthDay")));
    }
    
    public Select monthOfBirth() {
        return new Select(driver.findElement(By.name("DateOfBirthMonth")));
    }
    
    public Select yearOfBirth() {
        return new Select(driver.findElement(By.name("DateOfBirthYear")));
    }
    
    // Email field
    public WebElement email() {
        return driver.findElement(By.id("Email"));
    }
    
    // Password fields
    public WebElement password() {
        return driver.findElement(By.id("Password"));
    }
    
    public WebElement confirmPassword() {
        return driver.findElement(By.id("ConfirmPassword"));
    }
    
    // Register button
    public WebElement registerButton() {
        return driver.findElement(By.id("register-button"));
    }
    
    // Success message
    public WebElement successMessage() {
        return driver.findElement(By.cssSelector(".result"));
    }
    
    // Methods
    public void clickregister() {
        registerlink().click();
    }
    
    public void selectGender(String gender) {
        if (gender.equalsIgnoreCase("male")) {
            maleGender().click();
        } else {
            femaleGender().click();
        }
    }
    
    public void enterNames(String firstName, String lastName) {
        this.firstName().sendKeys(firstName);
        this.lastName().sendKeys(lastName);
    }
    
    public void selectDateOfBirth(String day, String month, String year) {
        dayOfBirth().selectByVisibleText(day);
        monthOfBirth().selectByVisibleText(month);
        yearOfBirth().selectByVisibleText(year);
    }
    
    public void enterEmail(String email) {
        this.email().sendKeys(email);
    }
    
    public void enterPasswords(String password, String confirmPassword) {
        this.password().sendKeys(password);
        this.confirmPassword().sendKeys(confirmPassword);
    }
    
    public void clickRegisterButton() {
        registerButton().click();
    }
} 