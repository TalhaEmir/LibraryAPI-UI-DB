package com.library.pages;

import com.library.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashBoardPage extends BooksPage {

    public DashBoardPage() {
        PageFactory.initElements(Driver.get(), this);
    }

    @FindBy(xpath="//span[.='Dashboard']")
    public WebElement dashboard;



    @FindBy(xpath="//span[.='Users']")
    public WebElement Users;


    @FindBy(xpath="//span[.='Books']")
    public WebElement Books;

    @FindBy(xpath="//span[text()='Hal Legros']")
    public WebElement userNameSpan;






    public void navigateDashBoard(String name) {
        switch(name){
            case "sabit":
                Users.click();
                break;
            case "Books":
                Books.click();
                break;
            default:
                System.out.println("Invalid navigation option");
                break;
        }
    }
}
