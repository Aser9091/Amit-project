package org.example.stepDefs;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;
import pages.CheckoutPage;
import static org.example.stepDefs.Hooks.driver;

public class PurchaseTwoProductsStepDef {
    HomePage homePage = new HomePage(driver);
    LoginPage loginPage = new LoginPage(driver);
    ProductPage productPage = new ProductPage(driver);
    CartPage cartPage = new CartPage(driver);
    CheckoutPage checkoutPage = new CheckoutPage(driver);

    @When("I add the first laptop to the cart")
    public void add_first_laptop() {
        // Wait for page to be fully loaded after login
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        homePage.selectCategory("Laptops");
        productPage.selectProductByIndex(0);
        productPage.addToCart();
    }

    @When("I validate that the first product is added to the cart")
    public void validate_first_product_added() {
        Assert.assertTrue(productPage.isAddToCartSuccess());
    }

    @When("I add the second laptop to the cart")
    public void add_second_laptop() {
        // Navigate back to the laptops category page
        driver.get("https://www.demoblaze.com/");
        homePage.selectCategory("Laptops");
        productPage.selectProductByIndex(1);
        productPage.addToCart();
    }

    @When("I validate that the second product is added to the cart")
    public void validate_second_product_added() {
        Assert.assertTrue(productPage.isAddToCartSuccess());
    }

    @When("I go to the cart")
    public void go_to_cart() {
        homePage.clickCartHeader();
        
        // Wait for cart page to load properly
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Then("both products should be visible in the cart with their titles and prices")
    public void both_products_visible() {
        Assert.assertTrue(cartPage.areBothProductsPresent());
    }

    @Then("the total amount should be calculated correctly")
    public void total_amount_correct() {
        Assert.assertTrue(cartPage.isTotalCorrect());
    }

    @When("I proceed to checkout and fill in the required details")
    public void proceed_to_checkout() {
        cartPage.clickPlaceOrder();
        checkoutPage.fillOrderDetails("Test User", "Country", "City", "4111111111111111", "12", "2025");
    }

    @When("I click on the Purchase button")
    public void click_purchase() {
        checkoutPage.clickPurchase();
    }

    @Then("a purchase success message should be displayed: {string}")
    public void purchase_success_message(String expectedMsg) {
        Assert.assertEquals(checkoutPage.getSuccessMessage(), expectedMsg);
    }
} 