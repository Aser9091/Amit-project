package org.example.stepDefs;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.HomePage;
import pages.SignUpPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.example.stepDefs.Hooks.driver;

public class SignUpStepDef {
    HomePage homePage = new HomePage(driver);
    SignUpPage signUpPage = new SignUpPage(driver);
    String newUsername = "testuser" + System.currentTimeMillis();
    String password = "Test@1234";

    @When("I enter a new username and password in the signup form")
    public void i_enter_new_username_password() {
        signUpPage.enterUsername(newUsername);
        signUpPage.enterPassword(password);
    }

    @When("I click on the Sign Up button")
    public void i_click_sign_up_button() {
        signUpPage.clickSignUpButton();
    }

    @Then("a sign up success message should be displayed: {string}")
    public void success_message_displayed(String expectedMsg) {
        String actualMsg = "";
        
        // First try to get message from alert
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.alertIsPresent());
            actualMsg = driver.switchTo().alert().getText();
            System.out.println("Found alert message: " + actualMsg);
            driver.switchTo().alert().accept();
        } catch (Exception alertException) {
            System.out.println("No alert found, trying modal...");
            
            // If no alert, try modal
            try {
                actualMsg = signUpPage.getModalMessage();
                System.out.println("Found modal message: " + actualMsg);
            } catch (Exception modalException) {
                System.out.println("Neither alert nor modal found");
                actualMsg = "";
            }
        }
        
        Assert.assertEquals(actualMsg, expectedMsg);
    }
} 