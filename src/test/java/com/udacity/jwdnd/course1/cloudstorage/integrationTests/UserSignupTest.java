package com.udacity.jwdnd.course1.cloudstorage.integrationTests;

import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserSignupTest {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignUpPage signUpPage;

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
        driver.get("http://localhost:" + port + "/signup");
        signUpPage = new SignUpPage(driver);
    }

    @Test
    void test_userSignup_happy_path() throws InterruptedException {
        signUpPage.setFirstName("John");
        signUpPage.setLastName("Doe");
        signUpPage.setUsername("Johnny");
        signUpPage.setPassword("password");
        signUpPage.submitForm();
        Thread.sleep(5000); //wait for feedback
        assertThat(signUpPage.getSignUpSuccess()).contains("login");
        signUpPage.clickLogin();
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl).contains("login");
    }
}
