package com.library.step_definitions.Talha;
import com.library.pages.BooksPage;
import com.library.pages.DashBoardPage;
import com.library.pages.SignInPage;
import com.library.pages.UsersPage;
import com.library.utilities.BrowserUtils;
import com.library.utilities.DB_Util;
import com.library.utilities.Driver;
import com.library.utilities.LibUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class LibraryStepDefinitions extends  SignInPage{


    RequestSpecification givenPart = given().log().all();
    Response response;
    JsonPath jp;
    ValidatableResponse thenPart;

   String role = System.getenv("role");
    BooksPage booksPage= new BooksPage();
    DashBoardPage dashboardPage= new DashBoardPage();
    UsersPage usersPage= new UsersPage();
   SignInPage signIn= new SignInPage();
   String token;



   String value="";

    Map<String, Object>randomMap;


    String category = "Librarian";


    public void chooseBookCategory(String category){

        int book_category_id = (int )randomMap.get("book_category_id");

        usersPage.selectUserGroupByText(category);

    }







    public  void bookSearch(){
        chooseBookCategory(category);
      String name= (String )randomMap.get("name");
        booksPage.searchButton.sendKeys(name);


    }


    public WebElement getWebElement(){
        String isbnApi= (String)randomMap.get("isbn");
        return Driver.get().findElement(By.xpath("//td[.='"+randomMap.get("name")+"']/preceding-sibling::td[1]"));
    }


    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a_TD(String role) {
        givenPart= givenPart.header("x-library-token", LibUtils.generateTokenByRole(role));
        System.out.println("LibUtils.generateTokenByRole(role) = " + LibUtils.generateTokenByRole(role));
    }





    @Given("Accept header is {string}")
    public void accept_header_is(String acceptType) {
        givenPart=givenPart.accept(acceptType);
        System.out.println("Accept Type = " + acceptType);
    }



    @Given("Path param is {string}")
    public void path_param_is(String pathParam) {
        givenPart = givenPart.pathParam("id", pathParam);

    }

    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_get_all_users_endpoint( String endPoint) {
        response = givenPart.when().get(endPoint);

    }



    @Then("status code should be {int}")
    public void status_code_should_be(int int1) {
        int statusCode= response.statusCode();
        assertEquals(int1,statusCode);
        System.out.println("Status Code = " + statusCode);
    }



    @Then("Response Content type is {string}")
    public void response_content_type_is(String contentType) {
        thenPart = response.then().contentType(contentType);
        System.out.println("Content Type = " + contentType);
        assertEquals(contentType,contentType);

    }

    @Then("{string} field should not be null")
    public void field_should_not_be_null(String userId) {
        jp = thenPart.extract().jsonPath();

        String userIdAPI = jp.getString(userId);

        System.out.println("userIdAPI = " + userIdAPI);


            assertNotNull(userIdAPI);




    }
    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String path) {
        jp = thenPart.extract().jsonPath();
        value = jp.getString(path);
        System.out.println("value = " + value);
        Assert.assertNotNull(value);


    }
    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> fields) {

        for (String field : fields) {
            thenPart.body(field, notNullValue());
            System.out.println("Field = " + field);
            Assert.assertNotNull(jp.getString(field));
        }






    }


    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String contentType) {
        givenPart = givenPart.header("Content-Type", contentType);

    }




    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String body) {

        switch(body){
            case "book":
                randomMap = LibUtils.createRandomBook();
                break;
            case "user":
                randomMap = LibUtils.createRandomUser();
                break;
            default:
                throw new IllegalArgumentException("Unsupported book type: " + body);
        }
        givenPart = givenPart.formParams(randomMap);

    }



    @Given("I logged in Library UI as {string}")
    public void i_logged_in_library_ui_as(String librarian) {
        signIn.login(librarian);

    }

    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String name) {
        dashboardPage.navigateDashBoard(name);
    }



    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endPoint) {

        response = givenPart.when().post(endPoint);
        thenPart = response.then();
        jp=thenPart.extract().jsonPath();



    }




    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String string, String string2) {

        String value = jp.getString(string);

        System.out.println("Value = " + value);

        assertEquals(string2, value);

        String user_id=jp.getString("user_id");

        System.out.println("user_id = " + user_id);


    }





    @Then("UI, Database and API created book information must match")
    public void ui_database_and_api_created_book_information_must_match() {


        String bookName = ((String) randomMap.get("name")).replace("'", "''");

        bookSearch(); // for searching book

        String isbnApi = (String) randomMap.get("isbn");

        System.out.println("isbnApi " + isbnApi);

        String bookIsbnUi = getWebElement().getText();

        System.out.println("bookIsbnUi = " + bookIsbnUi);

        assertEquals("ISBN does not match", isbnApi, bookIsbnUi);

        String query = "SELECT isbn FROM books WHERE name = '" + bookName + "';";

        DB_Util.runQuery(query);

        String isbnDB = DB_Util.getFirstRowFirstColumn();

        System.out.println("isbn = " + isbnDB);

        assertEquals("API and Database ISBN do not match", isbnApi, isbnDB);
        assertEquals("UI and Database ISBN do not match", bookIsbnUi, isbnDB);


    }


    @Then("user_id\" field should not be null")
    public void user_id_field_should_not_be_null() {

       jp=thenPart.extract().jsonPath();

        String userId = jp.getString("user_id");

        System.out.println("userId = " + userId);

        assertNotNull(userId);
    }


    @Then("created user information should match with Database")
    public void created_user_information_should_match_with_database() {

    String id= jp.getString("user_id");
        System.out.println("id = " + id);
        String query="select email,password from users where id="+ id;
    DB_Util.runQuery(query);
        Map<String ,String> userMap =DB_Util.getRowMap(1);
        assertEquals(randomMap.get("email"),userMap.get("email"));


    }

    @Then("created user should be able to login Library UI")
    public void created_user_should_be_able_to_login_library_ui() {

        jp=thenPart.extract().jsonPath();


        String password = (String) randomMap.get("password");

        System.out.println("password = " + password);

        String email = (String) randomMap.get("email");


        signIn.emailField.sendKeys(email);

        signIn.passwordField.sendKeys(password);

        BrowserUtils.waitFor(5);

        signIn.submitButton.click();


    }
    @Then("created user name should appear in Dashboard Page")
    public void created_user_name_should_appear_in_dashboard_page() {

        jp=thenPart.extract().jsonPath();

       String userName=Driver.get().findElement(By.xpath("//span[text()='"+randomMap.get("full_name")+"']")).getText();

        assertEquals(userName,randomMap.get("full_name"));




    }

    @Given("I logged Library api with credentials {string} and {string}")
    public void i_logged_library_api_with_credentials_and(String email, String password) {
        token=LibUtils.getToken(email,password);

    }
    @Given("I send {string} information as request body")
    public void i_send_information_as_request_body(String key) {
        givenPart.formParam(key,token);

    }




    }


















