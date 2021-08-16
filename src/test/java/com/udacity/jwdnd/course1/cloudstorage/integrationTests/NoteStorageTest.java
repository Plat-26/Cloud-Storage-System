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

/*
* Tests should be run separately to avoid authentication conflicts*/

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteStorageTest {

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
    @DisplayName("Test for saving notes")
    void test_noteStorage_happy_path() throws InterruptedException {
        createNote();
        String result = resultPage.getResultMessageDisplay();
        assertThat(result).isEqualTo("Success");

        backToNotesTab();

        String titleDisplay = homePage.getNoteTitleDisplay();
        assertThat(titleDisplay).isEqualTo("Some Note");

        String descDisplay = homePage.getNoteDescDisplay();
        assertThat(descDisplay).startsWith("Don't kill");
    }

    @Test
    @DisplayName("Test for viewing and deleting notes")
    void test_noteViewAndDelete_happy_path() throws InterruptedException {
        createNote();
        backToNotesTab();

        List<WebElement> noteList = driver.findElements(By.id("userTable"));
        assertThat(noteList).isNotEmpty();
        homePage.editNoteButton.click();
        Thread.sleep(3000);
        String titleInputField = homePage.getNoteTitleInputField();
        assertThat(titleInputField).contains("Some Note");

        String descInputField = homePage.getNoteDescInputField();
        assertThat(descInputField).contains("kill");

        homePage.closeNoteButton.click();
        Thread.sleep(3000);

        homePage.deleteNoteButton.click();
        String result = resultPage.getResultMessageDisplay();
        assertThat(result).isEqualTo("Success");

        backToNotesTab();

        noteList = driver.findElements(By.id("userTable"));
        assertThat(noteList).isEmpty();
    }

    private void createNote() throws InterruptedException {
        homePage.notesTab.click();
        Thread.sleep(5000);
        homePage.addNoteButton.click();
        homePage.setNoteTitle("Some Note");
        homePage.setNoteDescriptionField("Don't kill anyone");
        homePage.saveNoteButton.click();
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

    private void backToNotesTab() throws InterruptedException {
        resultPage.clickContinueLink();
        homePage.notesTab.click();
        Thread.sleep(3000);
    }
}
