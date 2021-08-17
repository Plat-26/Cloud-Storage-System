package com.udacity.jwdnd.course1.cloudstorage.integrationTests;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.ResultPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialStorageTest {
    @LocalServerPort
    private Integer port;

    private static WebDriver driver;
    private SignUpPage signUpPage;
    private LoginPage loginPage;
    private HomePage homePage;
    private ResultPage resultPage;


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
        resultPage = new ResultPage(driver);
        signUpUser();
        loginUser();
    }

    @Test
    @DisplayName("Test for saving credential")
    void test_credentialStorage_happy_path() throws InterruptedException {
        Thread.sleep(5000);
        createCredential();
        String result = resultPage.getResultMessageDisplay();
        assertThat(result).isEqualTo("Success");

        backToCredentialTab();

        String urlDisplay = homePage.getCredUrlDisplay();
        assertThat(urlDisplay).startsWith("www");

        String usernameDisplay = homePage.getCredUsernameDisplay();
        assertThat(usernameDisplay).isEqualTo("Plat");

        String passwordDisplay = homePage.getCredPasswordDisplay();
        assertThat(passwordDisplay).isNotBlank();
        assertThat(passwordDisplay).isNotEqualTo("super-secret-password");
    }

    @Test
    @DisplayName("Test for viewing and deleting credentials")
    void test_credentialViewAndDelete_happy_path() throws InterruptedException {
        Thread.sleep(3000);
        createCredential();
        backToCredentialTab();

        List<WebElement> credList = driver.findElements(By.id("credentialTable"));
        assertThat(credList).isNotEmpty();
        homePage.viewCredentialButton.click();
        Thread.sleep(3000);
        String urlField = homePage.getCredentialUrlField();
        assertThat(urlField).contains("www");

        String usernameField = homePage.getCredentialUsernameField();
        assertThat(usernameField).isEqualTo("Plat");

        String passwordField = homePage.getCredentialPasswordField();
        assertThat(passwordField).isEqualTo("super-secret-password");

        homePage.closeCredentialButton.click();
        Thread.sleep(3000);

        homePage.deleteCredentialButton.click();
        String result = resultPage.getResultMessageDisplay();
        assertThat(result).isEqualTo("Success");

        backToCredentialTab();

        credList = driver.findElements(By.id("credentialTable"));
        assertThat(credList).isEmpty();
    }

    private void createCredential() throws InterruptedException {
        homePage.credentialsTab.click();
        Thread.sleep(3000);
        homePage.addCredentialButton.click();
        Thread.sleep(3000);
        homePage.setCredentialUrlField("www.singers-and-hackers.com");
        homePage.setCredentialUsernameField("Plat");
        homePage.setCredentialPasswordField("super-secret-password");
        homePage.saveCredentialButton.click();
    }

    private void signUpUser() {
        signUpPage.setFirstName("John");
        signUpPage.setLastName("Doe");
        signUpPage.setUsername("johnny");
        signUpPage.setPassword("password");
        signUpPage.submitForm();
        signUpPage.clickLogin();
    }

    private void loginUser() {
        loginPage.setUsername("johnny");
        loginPage.setPassword("password");
        loginPage.submitForm();
    }

    private void backToCredentialTab() throws InterruptedException {
        resultPage.clickContinueLink();
        homePage.credentialsTab.click();
        Thread.sleep(3000);
    }

}
