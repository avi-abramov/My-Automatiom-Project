package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

class MyMain {

    public static void main(String[] args) {
        try {
            testCaseSearchHarryPotter();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static void testCaseSearchHarryPotter() throws InterruptedException {
        System.out.println("-----------");

        // Setup ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Configure ChromeOptions to start maximized
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        // Create ChromeDriver instance with ChromeOptions
        WebDriver driver = new ChromeDriver(options);

        try {
            // Navigate to Amazon.com
            driver.get("https://www.amazon.com");

            // Find the search box element and type "Harry Potter and the Order of the Phoenix"
            driver.findElement(By.cssSelector("#twotabsearchtextbox")).sendKeys("Harry Potter and the Order of the Phoenix\n");

            // Wait for the search results to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-component-type=\"s-result-info-bar\"] [class=\"a-section a-spacing-small a-spacing-top-small\"] span")));

            // Check the text that shows how many results were found and split it into words
            String str = driver.findElement(By.cssSelector("[data-component-type=\"s-result-info-bar\"] [class=\"a-section a-spacing-small a-spacing-top-small\"] span")).getText();
            String[] splitStr = str.split("\\s+");
            System.out.println(splitStr[2]);

            // Apply filters
            applyFilters(driver);

            // Find all elements of the book names and keep them in a list
            List<WebElement> myList = driver.findElements(By.cssSelector(".sg-col-inner a .a-size-medium"));

            // Print all book names
            for (WebElement element : myList) {
                System.out.println(element.getText());
            }

            // Find the longest book name
            String maxLengthStr = "";
            int maxLength = 0;
            for (WebElement element : myList) {
                String text = element.getText();
                int currNameLength = text.length();
                if (currNameLength > maxLength) {
                    maxLength = currNameLength;
                    maxLengthStr = text;
                }
            }

            // Print the longest book name found
            System.out.println("Longest book name: " + maxLengthStr);

            // Add the book to the cart
            addBookToCart(driver);

            // Click on the new selectors
            clickNewSelectors(driver);

            // Wait for 5 seconds before finishing the script
            Thread.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // Finally block is removed to keep the browser open
    }

    static void applyFilters(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Filter only books
        WebElement booksFilter = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#departments ul li:nth-child(4) a")));
        booksFilter.click();

        // Filter only English language
        WebElement englishFilter = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#p_n_feature_nine_browse-bin-title+ul div.a-checkbox")));
        englishFilter.click();
    }

    static void addBookToCart(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Select the specific book
        WebElement bookElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#search > div.s-desktop-width-max.s-desktop-content.s-opposite-dir.s-wide-grid-style.sg-row > div.sg-col-20-of-24.s-matching-dir.sg-col-16-of-20.sg-col.sg-col-8-of-12.sg-col-12-of-16 > div > span.rush-component.s-latency-cf-section > div.s-main-slot.s-result-list.s-search-results.sg-row > div:nth-child(6) > div > div > span > div > div > div > div.puisg-col.puisg-col-4-of-12.puisg-col-8-of-16.puisg-col-12-of-20.puisg-col-12-of-24.puis-list-col-right > div > div > div.a-section.a-spacing-none.puis-padding-right-small.s-title-instructions-style > h2 > a > span")));
        bookElement.click();

        // Add to cart
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
        addToCartButton.click();

        // Optional: wait for the cart confirmation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".a-box-inner .a-alert-heading")));
    }

    static void clickNewSelectors(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click on the first new selector
        WebElement firstNewSelectorElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#audibleCashAccordionRow > div > div.a-accordion-row-a11y.a-accordion-row.a-declarative.a-accordion-sr.accordion-header.mobb-header-css > i")));
        firstNewSelectorElement.click();

        // Click on the second new selector
        WebElement secondNewSelectorElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#audibleCashAccordionRow > div > div.a-accordion-row-a11y.a-accordion-row.a-declarative.a-accordion-sr.accordion-header.mobb-header-css")));
        secondNewSelectorElement.click();
    }
}
