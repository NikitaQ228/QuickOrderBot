package ru.nikita.QuickOrderBot;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginAndLogoutTest {
    private static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromiumdriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void testLoginAndLogout() {
        driver.get("http://localhost:8080/login");

        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        usernameInput.sendKeys("admin@example.com");
        passwordInput.sendKeys("admin");

        passwordInput.submit();

        assertTrue(driver.getCurrentUrl().endsWith("/view/foods"));

        WebElement logoutForm = driver.findElement(By.cssSelector("form.logout-form"));

        logoutForm.findElement(By.cssSelector("input[type='submit']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> d.getCurrentUrl().contains("/login")
                        || d.getCurrentUrl().equals("http://localhost:8080/"));

        assertTrue(driver.getCurrentUrl().contains("/login")
                || driver.getCurrentUrl().equals("http://localhost:8080/"));

        assertFalse(driver.getPageSource().contains("Выйти"));
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
