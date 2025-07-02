package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class P03_homePage {
    public WebDriver driver;

    public P03_homePage(WebDriver driver) {
        this.driver = driver;
    }
    
    // Currency dropdown
    public Select currencyDropdown() {
        return new Select(driver.findElement(By.id("customerCurrency")));
    }
    
    // Product prices (for currency verification)
    public List<WebElement> productPrices() {
        return driver.findElements(By.cssSelector(".actual-price"));
    }
    
    // Search box
    public WebElement searchBox() {
        return driver.findElement(By.id("small-searchterms"));
    }
    
    // Search button
    public WebElement searchButton() {
        return driver.findElement(By.cssSelector("button[type=\"submit\"]"));
    }
    
    // Search results
    public List<WebElement> searchResults() {
        return driver.findElements(By.cssSelector(".product-item"));
    }
    
    // Product SKU in search results
    public List<WebElement> productSkus() {
        return driver.findElements(By.cssSelector(".sku-number"));
    }
    
    // Main categories
    public List<WebElement> mainCategories() {
        return driver.findElements(By.cssSelector(".top-menu > li"));
    }
    
    // Sub categories
    public List<WebElement> subCategories() {
        return driver.findElements(By.cssSelector(".sublist > li"));
    }
    
    // Page title
    public WebElement pageTitle() {
        return driver.findElement(By.cssSelector("div[class=\"page-title\"] h1"));
    }
    
    // First slider (Nokia)
    public WebElement firstSlider() {
        return driver.findElement(By.cssSelector("a[href*=\"nokia-lumia-1020\"]"));
    }
    
    // Second slider (iPhone)
    public WebElement secondSlider() {
        return driver.findElement(By.cssSelector("a[href*=\"iphone-6\"]"));
    }
    
    // Follow us links
    public WebElement facebookLink() {
        return driver.findElement(By.cssSelector("a[href*=\"facebook.com\"]"));
    }
    
    public WebElement twitterLink() {
        return driver.findElement(By.cssSelector("a[href*=\"twitter.com\"]"));
    }
    
    public WebElement rssLink() {
        return driver.findElement(By.cssSelector("a[href*=\"news/rss\"]"));
    }
    
    public WebElement youtubeLink() {
        return driver.findElement(By.cssSelector("a[href*=\"youtube.com\"]"));
    }
    
    // Wishlist button for HTC product
    public WebElement htcWishlistButton() {
        return driver.findElement(By.cssSelector("div[data-productid=\"18\"] .add-to-wishlist-button"));
    }
    
    // Success message for wishlist
    public WebElement wishlistSuccessMessage() {
        return driver.findElement(By.cssSelector(".content"));
    }
    
    // Wishlist tab
    public WebElement wishlistTab() {
        return driver.findElement(By.cssSelector("a[class=\"ico-wishlist\"]"));
    }
    
    // Wishlist quantity
    public WebElement wishlistQuantity() {
        return driver.findElement(By.cssSelector(".wishlist-qty"));
    }
    
    // Methods
    public void selectEuroCurrency() {
        currencyDropdown().selectByVisibleText("Euro");
    }
    
    public void searchForProduct(String productName) {
        searchBox().sendKeys(productName);
        searchButton().click();
    }
    
    public void searchForSku(String sku) {
        searchBox().sendKeys(sku);
        searchButton().click();
    }
    
    public void clickFirstSlider() {
        firstSlider().click();
    }
    
    public void clickSecondSlider() {
        secondSlider().click();
    }
    
    public void clickFacebookLink() {
        facebookLink().click();
    }
    
    public void clickTwitterLink() {
        twitterLink().click();
    }
    
    public void clickRssLink() {
        rssLink().click();
    }
    
    public void clickYoutubeLink() {
        youtubeLink().click();
    }
    
    public void addHtcToWishlist() {
        htcWishlistButton().click();
    }
    
    public void clickWishlistTab() {
        wishlistTab().click();
    }
} 