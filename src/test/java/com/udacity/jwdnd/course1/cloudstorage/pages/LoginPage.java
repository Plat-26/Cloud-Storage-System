package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(tagName = "button")
    private WebElement loginButton;

    @FindBy(id = "invalidDataDiv")
    private WebElement invalidDataDiv;

    @FindBy(id = "loggedOutDiv")
    private WebElement loggedOutDiv;

    @FindBy(css = "div label a")
    private WebElement signupLink;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void setUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void setPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void submitForm() {
        loginButton.click();
    }

    public String getInvalidDataDiv() {
        return invalidDataDiv.getText();
    }

    public String getLoggedOutDiv() {
        return loggedOutDiv.getText();
    }

    public void clickSignupLink() {
        signupLink.click();
    }
}
