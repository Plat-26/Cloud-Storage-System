package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameField;

    @FindBy(id = "inputLastName")
    private WebElement lastNameField;

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(tagName = "button")
    private WebElement submitButton;

    @FindBy(id = "signupSuccess")
    private WebElement signUpSuccessDiv;

    @FindBy(linkText = "login")
    private WebElement loginLink;

    @FindBy(id = "sign-up-error")
    private WebElement errorMsgDiv;

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setFirstName(String firstName) {
        firstNameField.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        lastNameField.sendKeys(lastName);
    }

    public void setUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void submitForm() {
        submitButton.click();
    }

    public String getSignUpSuccess() {
        return signUpSuccessDiv.getText();
    }

    public void clickLogin() {
        loginLink.click();
    }

    public String getErrorText() {
        return errorMsgDiv.getText();
    }

}
