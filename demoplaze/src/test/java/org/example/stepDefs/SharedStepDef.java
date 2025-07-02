package org.example.stepDefs;

import io.cucumber.java.en.*;
import pages.HomePage;
import pages.LoginPage;
import pages.SignUpPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import java.time.Duration;

public class SharedStepDef {
    HomePage homePage;
    LoginPage loginPage;
    SignUpPage signUpPage;
    
    private String testUsername = "testuser" + System.currentTimeMillis();
    private String testPassword = "Test@1234";

    @Given("I am on the Demoblaze homepage")
    public void i_am_on_homepage() {
        Hooks.driver.get("https://www.demoblaze.com/");
    }

    @Given("I am logged in to Demoblaze")
    public void i_am_logged_in() {
        Hooks.driver.get("https://www.demoblaze.com/");
        
        // Initialize page objects
        homePage = new HomePage(Hooks.driver);
        loginPage = new LoginPage(Hooks.driver);
        signUpPage = new SignUpPage(Hooks.driver);
        
        // First try to sign up with a new user
        try {
            homePage.clickSignUpHeader();
            Thread.sleep(1000); // Wait for modal to appear
            signUpPage.enterUsername(testUsername);
            signUpPage.enterPassword(testPassword);
            signUpPage.clickSignUpButton();
            
            // Accept the signup alert
            try {
                WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.alertIsPresent());
                String alertText = Hooks.driver.switchTo().alert().getText();
                System.out.println("Signup alert: " + alertText);
                Hooks.driver.switchTo().alert().accept();
            } catch (Exception e) {
                // If no alert, user might already exist, continue with login
            }
            
            // Close signup modal if it's still open
            try {
                Hooks.driver.findElement(By.cssSelector(".modal-header .close")).click();
            } catch (Exception e) {
                // Modal already closed
            }
        } catch (Exception e) {
            System.out.println("Signup failed: " + e.getMessage());
        }
        
        // Now login
        try {
            homePage.clickLoginHeader();
            Thread.sleep(1000); // Wait for modal to appear
            loginPage.enterUsername(testUsername);
            loginPage.enterPassword(testPassword);
            loginPage.clickLoginButton();
            
            // Accept the login alert (success or failure)
            try {
                WebDriverWait wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(5));
                wait.until(ExpectedConditions.alertIsPresent());
                String alertText = Hooks.driver.switchTo().alert().getText();
                System.out.println("Login alert: " + alertText);
                Hooks.driver.switchTo().alert().accept();
            } catch (Exception e) {
                // If no alert, login might have failed silently
            }
            
            // Close login modal if it's still open
            try {
                Hooks.driver.findElement(By.cssSelector(".modal-header .close")).click();
            } catch (Exception e) {
                // Modal already closed
            }
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    @When("I click on the Sign up button in the header")
    public void i_click_sign_up_header() {
        if (homePage == null) {
            homePage = new HomePage(Hooks.driver);
        }
        homePage.clickSignUpHeader();
    }
} 