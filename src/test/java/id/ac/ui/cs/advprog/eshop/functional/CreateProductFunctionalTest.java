package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.time.Duration;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;
    private WebDriverWait wait;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProductAndVerifyInList(ChromeDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Generate unique product name
        String uniqueProductName = "TestProduct_" + System.currentTimeMillis();
        String productQuantity = "15";

        System.out.println("Testing product: " + uniqueProductName);

        // Navigate to product list page
        driver.get(baseUrl + "/product/list");
        wait.until(ExpectedConditions.titleContains("Product List"));
        System.out.println("1. Reached Product List page");

        // Click "Create New Product" button
        WebElement createButton = null;
        try {
            createButton = driver.findElement(By.xpath("//a[contains(text(), 'Create New Product')]"));
        } catch (Exception e1) {
            try {
                // Fallback: cari dengan class atau atribut lain
                createButton = driver.findElement(By.cssSelector("a.btn-primary"));
            } catch (Exception e2) {
                fail("Cannot find 'Create New Product' button. Check your productList.html template.");
            }
        }

        createButton.click();
        wait.until(ExpectedConditions.titleContains("Create New Product"));
        System.out.println("2. Reached Create Product page");

        // Verify form elements exist
        WebElement nameInput = driver.findElement(By.id("productName"));
        WebElement quantityInput = driver.findElement(By.id("productQuantity"));

        assertTrue(nameInput.isDisplayed(), "Product name input should be visible");
        assertTrue(quantityInput.isDisplayed(), "Product quantity input should be visible");

        // Fill the form
        nameInput.clear();
        nameInput.sendKeys(uniqueProductName);

        quantityInput.clear();
        quantityInput.sendKeys(productQuantity);

        // Submit form
        WebElement submitButton = null;
        try {
            submitButton = driver.findElement(By.cssSelector("button[type='submit'].btn-primary"));
        } catch (Exception e) {
            // Fallback: cari button dengan teks "Submit"
            submitButton = driver.findElement(By.xpath("//button[contains(text(), 'Submit')]"));
        }

        submitButton.click();
        System.out.println("3. Form submitted");

        // Verify redirect to product list
        wait.until(ExpectedConditions.titleContains("Product List"));
        assertTrue(driver.getCurrentUrl().contains("/product/list"),
                "Should redirect to product list after submission");
        System.out.println("4. Redirected back to Product List");

        // Verify the new product appears in the list
        // Cek di seluruh halaman
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains(uniqueProductName),
                "Product name '" + uniqueProductName + "' should appear on the page");

        // Cek di tabel (lebih spesifik)
        List<WebElement> tableRows = driver.findElements(By.cssSelector("table tbody tr"));
        boolean productFoundInTable = false;
        String productRowText = "";

        for (WebElement row : tableRows) {
            productRowText = row.getText();
            if (productRowText.contains(uniqueProductName)) {
                productFoundInTable = true;
                System.out.println("Found product in row: " + productRowText);

                // Verifikasi quantity juga ada di row yang sama
                assertTrue(productRowText.contains(productQuantity),
                        "Product quantity should be in the same row");
                break;
            }
        }

        assertTrue(productFoundInTable,
                "New product should be found in the product list table");

        // Verify badge color
        try {
            // Cari badge di row yang sama dengan product name
            WebElement quantityCell = driver.findElement(
                    By.xpath("//td[contains(text(), '" + uniqueProductName + "')]/following-sibling::td")
            );

            List<WebElement> badges = quantityCell.findElements(By.cssSelector("span.badge"));
            if (!badges.isEmpty()) {
                WebElement badge = badges.get(0);
                String badgeClass = badge.getAttribute("class");
                String badgeText = badge.getText();

                System.out.println("Badge class: " + badgeClass + ", text: " + badgeText);

                // Verifikasi badge text sesuai quantity
                assertEquals(productQuantity, badgeText,
                        "Badge should display the correct quantity");

                // Verifikasi warna badge berdasarkan quantity
                int quantity = Integer.parseInt(productQuantity);
                if (quantity > 10) {
                    assertTrue(badgeClass.contains("badge-success"),
                            "Quantity > 10 should have green badge");
                } else if (quantity > 0) {
                    assertTrue(badgeClass.contains("badge-warning"),
                            "Quantity 1-10 should have yellow badge");
                } else {
                    assertTrue(badgeClass.contains("badge-danger"),
                            "Quantity 0 should have red badge");
                }
            }
        } catch (Exception e) {
            System.out.println("Note: No badge found or badge verification skipped: " + e.getMessage());
        }

        System.out.println("✅ Test passed successfully!");
    }

    @Test
    void testEmptyProductNameValidation(ChromeDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Navigate langsung ke create page
        driver.get(baseUrl + "/product/create");

        // Isi hanya quantity
        WebElement nameInput = driver.findElement(By.id("productName"));
        WebElement quantityInput = driver.findElement(By.id("productQuantity"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        // Kosongkan name field
        nameInput.clear();
        quantityInput.sendKeys("10");

        // Submit form
        submitButton.click();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore
        }

        // Verifikasi: masih di halaman create
        boolean stillOnCreatePage = driver.getCurrentUrl().contains("/product/create");
        boolean hasRequiredAttribute = nameInput.getAttribute("required") != null;
        boolean pageHasValidationMessage = driver.getPageSource().contains("required") ||
                driver.getPageSource().contains("fill out");

        assertTrue(stillOnCreatePage || hasRequiredAttribute || pageHasValidationMessage,
                "Form should not submit with empty product name (HTML5 validation)");
    }

    @Test
    void testNavigationFromCreatePage(ChromeDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        // Navigate ke create page
        driver.get(baseUrl + "/product/create");

        // Test "Back to List" button
        WebElement backButton = null;
        try {
            backButton = driver.findElement(By.xpath("//a[contains(text(), 'Back to List')]"));
        } catch (Exception e) {
            backButton = driver.findElement(By.xpath("//a[contains(@href, '/product/list')]"));
        }

        assertNotNull(backButton, "Should have a 'Back to List' button");
        backButton.click();

        // Verifikasi kembali ke list page
        wait.until(ExpectedConditions.titleContains("Product List"));
        assertTrue(driver.getCurrentUrl().contains("/product/list"),
                "Should navigate back to product list");
    }
}