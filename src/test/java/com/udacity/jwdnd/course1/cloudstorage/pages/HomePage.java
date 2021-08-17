package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "nav-files")
    public WebElement filesTab;

    @FindBy(id = "nav-notes-tab")
    public WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    public WebElement credentialsTab;

    @FindBy(id = "fileUpload")
    public WebElement uploadFileButton;

    @FindBy(id = "credential-url")
    public WebElement submitFileButton;

    @FindBy(id = "file-view")
    public WebElement viewFileButton;

    @FindBy(id = "file-delete")
    public WebElement deleteFileButton;

    @FindBy(id = "addNote")
    public WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(id = "saveNote")
    public WebElement saveNoteButton;

    @FindBy(id = "closeNote")
    public WebElement closeNoteButton;

    @FindBy(css = "#userTable button")
    public WebElement editNoteButton;

    @FindBy(css = "#userTable a")
    public WebElement deleteNoteButton;

    @FindBy(id = "note-title-display")
    private WebElement noteTitleDisplay;

    @FindBy(id = "note-desc-display")
    private WebElement noteDescDisplay;

    @FindBy(id = "addCredential")
    public WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "closeCredential")
    public WebElement closeCredentialButton;

    @FindBy(id = "saveCredential")
    public WebElement saveCredentialButton;

    @FindBy(id = "cred-url-display")
    private WebElement credUrlDisplay;

    @FindBy(id = "cred-username-display")
    private WebElement credUsernameDisplay;

    @FindBy(id = "cred-password-display")
    private WebElement credPasswordDisplay;

    @FindBy(css = "#credentialTable button")
    public WebElement viewCredentialButton;

    @FindBy(css = "#credentialTable a")
    public WebElement deleteCredentialButton;

    @FindBy(css = "#logoutDiv button")
    public WebElement logoutButton;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setNoteTitle(String title) {
        noteTitleField.sendKeys(title);
    }

    public void setNoteDescriptionField(String desc) {
        noteDescriptionField.sendKeys(desc);
    }

    public String getNoteTitleDisplay() {
        return noteTitleDisplay.getText();
    }

    public String getNoteDescDisplay() {
        return noteDescDisplay.getText();
    }

    public void setCredentialUrlField(String url) {
        credentialUrlField.sendKeys(url);
    }

    public void setCredentialUsernameField(String username) {
        credentialUsernameField.sendKeys(username);
    }

    public void setCredentialPasswordField(String password) {
        credentialPasswordField.sendKeys(password);
    }

    public String getCredUrlDisplay() {
        return credUrlDisplay.getText();
    }

    public String getCredUsernameDisplay() {
        return credUsernameDisplay.getText();
    }

    public String getCredPasswordDisplay() {
        return credPasswordDisplay.getText();
    }

    public String getNoteTitleInputField() {
        return noteTitleField.getAttribute("value");
    }

    public String getNoteDescInputField() {
        return noteDescriptionField.getAttribute("value");
    }

    public String getCredentialUrlField() {
        return credentialUrlField.getAttribute("value");
    }

    public String getCredentialUsernameField() {
        return credentialUsernameField.getAttribute("value");
    }

    public String getCredentialPasswordField() {
        return credentialPasswordField.getAttribute("value");
    }

}
