package com.library.pages;

import com.library.utilities.BrowserUtils;
import com.library.utilities.Driver;
import com.library.utilities.LibUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;


public class SignInPage extends DashBoardPage {
    public SignInPage() {
        PageFactory.initElements(Driver.get(), this);
    }


    BooksPage bookPage = new BooksPage();


    @FindBy(id="inputEmail")
    public WebElement emailField;




    @FindBy(id="inputPassword")
    public WebElement passwordField;

    @FindBy(xpath="//button[@type='submit']")
    public WebElement submitButton;

    public void  login(String role){
        Map<String,String>roleCredentials=  (LibUtils.returnCredentials(role));
        String email= roleCredentials.get("email");
        String password= roleCredentials.get("password");
       login(email,password);
        bookPage.click();
        BrowserUtils.waitFor(5);

    }



    public void login(String email, String password){

        emailField.sendKeys(System.getenv("librarian_username"));
        passwordField.sendKeys(System.getenv("librarian_password"));
        submitButton.click();


    }


    public void submit(String email, String password){

        emailField.sendKeys(System.getenv("librarian_username"));
        passwordField.sendKeys(System.getenv("librarian_password"));


    }


}
