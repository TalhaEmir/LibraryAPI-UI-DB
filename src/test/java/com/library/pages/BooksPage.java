package com.library.pages;

import com.library.utilities.Driver;
import org.junit.validator.PublicClassValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class BooksPage {

    public BooksPage() {
        PageFactory.initElements(Driver.get(), this);
    }




    @FindBy(xpath="//a[@href='tpl/add-book.html']")
    public WebElement addBookButton;

    @FindBy(xpath = "//input[@type='search']")
    public WebElement searchButton;


    @FindBy(xpath = "//select[@id='book_categories']")
    public WebElement categoryDropdown;

    @FindBy(xpath = "//a[@href='#books']")
    public WebElement BooksButton;

    int value = 0;



    public void click(){
        BooksButton.click();

    }



    public void search(String name){
        searchButton.sendKeys(name);
        searchButton.submit();

    }



    }

