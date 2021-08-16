package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(css = "#successDiv h1")
    private WebElement resultMessageDisplay;

    @FindBy(css = "div a")
    private WebElement continueButton;

    public String getResultMessageDisplay() {
        return resultMessageDisplay.getText();
    }

    public void clickContinueLink() {
        continueButton.click();
    }

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
