package com.udacity.jwdnd.course1.cloudstorage.integrationTests;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserAuthTest {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignUpPage signUpPage;
    private LoginPage loginPage;
    private HomePage homePage;

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
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
    }

    @Test
    void test_userSignup_happy_path() throws InterruptedException {
        signUpUser();
        Thread.sleep(5000); //wait for feedback
        assertThat(signUpPage.getSignUpSuccess()).contains("login");
        signUpPage.clickLogin();
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl).contains("login");
    }

    @Test
    void test_user_login_and_logout() throws InterruptedException {
        signUpUser();
        loginUser();

        homePage.logoutButton.click();
        Thread.sleep(2000);
        String currentUrl = driver.getCurrentUrl();
        assertThat(currentUrl).contains("login");
        assertThat(currentUrl).contains("logout");
    }

    @Test
    void test_userLogin_sad_path() {
        driver.get("http://localhost:" + port + "/login");
        loginPage.setUsername("johnny");
        loginPage.setPassword("password");
        loginPage.submitForm();

        String errorMsg = loginPage.getInvalidDataDiv();
        assertThat(errorMsg.contains("invalid"));
    }

    private void signUpUser() {
        signUpPage.setFirstName("John");
        signUpPage.setLastName("Doe");
        signUpPage.setUsername("johnny");
        signUpPage.setPassword("password");
        signUpPage.submitForm();
    }

    private void loginUser() {
        signUpPage.clickLogin();
        loginPage.setUsername("johnny");
        loginPage.setPassword("password");
        loginPage.submitForm();
    }
}
