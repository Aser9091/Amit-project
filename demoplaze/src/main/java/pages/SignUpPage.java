package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SignUpPage {
    WebDriver driver;
    public SignUpPage(WebDriver driver) { this.driver = driver; }

    public void waitForSignUpModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sign-username")));
    }

    public void enterUsername(String username) {
        waitForSignUpModal();
        driver.findElement(By.id("sign-username")).clear();
        driver.findElement(By.id("sign-username")).sendKeys(username);
    }
    public void enterPassword(String password) {
        waitForSignUpModal();
        driver.findElement(By.id("sign-password")).clear();
        driver.findElement(By.id("sign-password")).sendKeys(password);
    }
    public void clickSignUpButton() {
        driver.findElement(By.xpath("//button[text()='Sign up']")).click();
    }
    public void waitForAlert() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.alertIsPresent());
    }
    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }
    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }
    public String getModalMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.modal-body")));
            String message = driver.findElement(By.cssSelector("div.modal-body")).getText();
            System.out.println("Found modal message: " + message);
            return message;
        } catch (Exception e) {
            System.out.println("First modal selector failed, trying alternatives...");
            // Try alternative selectors if the first one fails
            try {
                String message = driver.findElement(By.cssSelector(".modal-content")).getText();
                System.out.println("Found modal message with .modal-content: " + message);
                return message;
            } catch (Exception e2) {
                try {
                    String message = driver.findElement(By.cssSelector(".modal")).getText();
                    System.out.println("Found modal message with .modal: " + message);
                    return message;
                } catch (Exception e3) {
                    System.out.println("All modal selectors failed, trying to find any visible modal...");
                    // Try to find any visible modal element
                    try {
                        String message = driver.findElement(By.cssSelector("[class*='modal']")).getText();
                        System.out.println("Found modal message with [class*='modal']: " + message);
                        return message;
                    } catch (Exception e4) {
                        System.out.println("No modal found, returning empty string");
                        return "";
                    }
                }
            }
        }
    }
} 