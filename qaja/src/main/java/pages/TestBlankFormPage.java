package pages;

import extensions.ReporterExtensions;
import extensions.WebDriverExtensions;
import io.qameta.allure.Step;
import models.Form;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TestBlankFormPage extends BasePage {
    public TestBlankFormPage(WebDriver driver) {
        super(driver);
    }

    @Step("Fill first name - {0}")
    public TestBlankFormPage fillFirstName(String name) {
        ReporterExtensions.log("Fill first name - '" + name + "'");
        firstNameInput.sendKeys(name);

        return this;
    }

    @Step("Fill last name - {0}")
    public TestBlankFormPage fillLastName(String name) {
        ReporterExtensions.log("Fill last name - '" + name + "'");
        lastNameInput.sendKeys(name);

        return this;
    }

    @Step("Fill email - {0}")
    public TestBlankFormPage fillEmail(String email) {
        ReporterExtensions.log("Fill email - '" + email + "'");
        emailInput.sendKeys(email);

        return this;
    }

    @Step("Fill comment - {0}")
    public TestBlankFormPage fillComment(String comment) {
        ReporterExtensions.log("Fill comment - '" + comment + "'");
        commentInput.sendKeys(comment);

        return this;
    }

    @Step("Click submit button")
    public TestBlankFormPage clickSubmitButton() {
        ReporterExtensions.log("Click submit button");
        submitButton.click();

        return this;
    }

    @Step("Submit form")
    public TestBlankFormPage sendForm(String firstName, String lastName, String email, String comment) throws Exception {
        fillFirstName(firstName);
        fillLastName(lastName);
        fillEmail(email);
        fillComment(comment);
        clickSubmitButton();

        return this;
    }

    @Step("Submit form")
    public TestBlankFormPage sendForm(Form form) throws Exception {
        fillFirstName(form.firstName);
        fillLastName(form.lastName);
        fillEmail(form.email);
        fillComment(form.comment);
        clickSubmitButton();

        return this;
    }

    @Step("Check success message displayed")
    public Boolean isSuccessMessageDisplayed(long timeout) {
        ReporterExtensions.log("Check success message displayed");

        return WebDriverExtensions.waitUntilElementIsDisplay(driver, By.xpath(SUCCESS_MESSAGE), timeout);
    }
    
   //Webelement Object Repository
    @FindBy(name = "wpforms[fields][0][first]")
    WebElement firstNameInput;

    @FindBy(name = "wpforms[fields][0][last]")
    WebElement lastNameInput;

    @FindBy(name = "wpforms[fields][1]")
    WebElement emailInput;

    @FindBy(name = "wpforms[fields][2]")
    WebElement commentInput;

    @FindBy(name = "wpforms[submit]")
    WebElement submitButton;

    static final String SUCCESS_MESSAGE = "//p[text()='Thanks for contacting us! We will be in touch with you shortly.']";
}
