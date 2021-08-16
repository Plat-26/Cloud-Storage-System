package com.udacity.jwdnd.course1.cloudstorage.integrationTests;

import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserLoginTest {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private LoginPage loginPage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    static void afterAll() {
        driver.quit();
    }

    @BeforeEach
    void setUp() {
        driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(driver);
    }

    ///TODO:Debug class
    @Test
    void test_userLogin_happy_path() throws InterruptedException {
        loginPage.setUsername("johnny");
        loginPage.setPassword("password");
        loginPage.submitForm();
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl).contains("home");
    }

    @Test
    void test_userLogin_sad_path() {
        loginPage.setUsername("johnny");
        loginPage.setPassword("password");
        loginPage.submitForm();

        String errorMsg = loginPage.getInvalidDataDiv();
        assertThat(errorMsg.contains("invalid"));
    }
}
