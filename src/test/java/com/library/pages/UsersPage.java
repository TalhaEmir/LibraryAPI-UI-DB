package com.library.pages;

import com.library.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class UsersPage {

    public UsersPage() {
        PageFactory.initElements(Driver.get(), this);
    }

    @FindBy(xpath="//input[@type='search']")
    public WebElement searchButton;

    @FindBy(xpath="//a[@href='tpl/add-user.html']")
    public WebElement addUserButton;

    @FindBy(xpath = "//select[@id='user_groups']")
    public WebElement userGroupsDropdown;








    // Dropdown'dan değer seçme
    public void selectUserGroupByText(String selectName) {
        switch(selectName){
            case "All":
                new Select(userGroupsDropdown).selectByVisibleText("ALL");
                break;
            case "Librarian":
                new Select(userGroupsDropdown).selectByVisibleText("Librarian");
                break;
            case "Students":
                new Select(userGroupsDropdown).selectByVisibleText("Students");
                break;
        }
    }
}
