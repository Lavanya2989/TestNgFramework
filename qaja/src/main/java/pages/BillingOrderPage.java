package pages;

import extensions.ReporterExtensions;
import extensions.WebElementExtensions;
import models.BillingOrder;
import dictionaries.State;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import extensions.WebDriverExtensions;
import io.qameta.allure.Step;

import java.util.ArrayList;

public class BillingOrderPage extends BasePage{
   
    public BillingOrderPage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Fill first name - {0}")
    public BillingOrderPage fillFirstName(String name) {
        ReporterExtensions.log("Fill first name - '" + name + "'");
        firstNameInput.sendKeys(name);

        return this;
    }

    @Step("Fill last name - {0}")
    public BillingOrderPage fillLastName(String name) {
        ReporterExtensions.log("Fill last name - '" + name + "'");
        lastNameInput.sendKeys(name);

        return this;
    }

    @Step("Fill email - {0}")
    public BillingOrderPage fillEmail(String email) {
        ReporterExtensions.log("Fill email - '" + email + "'");
        emailInput.sendKeys(email);

        return this;
    }

    @Step("Fill phone - {0}")
    public BillingOrderPage fillPhone(String phone) {
        ReporterExtensions.log("Fill phone - '" + phone + "'");
        phoneInput.sendKeys(phone);

        return this;
    }

    @Step("Fill address line 1 - {0}")
    public BillingOrderPage fillAddressLine1(String address) {
        ReporterExtensions.log("Fill address line 1 - '" + address + "'");
        addressLine1Input.sendKeys(address);

        return this;
    }

    @Step("Fill address line 2 - {0}")
    public BillingOrderPage fillAddressLine2(String address) {
        ReporterExtensions.log("Fill address line 2 - '" + address + "'");
        addressLine2Input.sendKeys(address);

        return this;
    }

    @Step("Fill city - {0}")
    public BillingOrderPage fillCity(String city) {
        ReporterExtensions.log("Fill city - '" + city + "'");
        cityInput.sendKeys(city);

        return this;
    }

    @Step("Fill zip code - {0}")
    public BillingOrderPage fillZipCode(String code) {
        ReporterExtensions.log("Fill code - '" + code + "'");
        zipCodeInput.sendKeys(code);

        return this;
    }

    @Step("Click state dropdown")
    public BillingOrderPage clickStateSelect() {
        ReporterExtensions.log("Click state dropdown");
        stateSelect.click();

        return this;
    }

    @Step("Click state option")
    public BillingOrderPage clickStateOption(State state) {
        ReporterExtensions.log("Click state option - '" + state + "'");
        stateOption = driver.findElement(By.xpath(STATE_OPTION.replace("{code}", state.toString())));
        stateOption.click();

        return this;
    }

    @Step("Fill comment - {0}")
    public BillingOrderPage fillComment(String comment) {
        ReporterExtensions.log("Fill comment - '" + comment + "'");
        commentInput.sendKeys(comment);

        return this;
    }

    @Step("Click first item radio button")
    public BillingOrderPage clickFirstItemRadioButton() {
        ReporterExtensions.log("Click first item radio button");
        firstItemRadioButton.click();

        return this;
    }

    @Step("Click second item radio button")
    public BillingOrderPage clickSecondItemRadioButton() {
        ReporterExtensions.log("Click second item radio button");
        secondItemRadioButton.click();

        return this;
    }

    @Step("Click third item radio button")
    public BillingOrderPage clickThirdItemRadioButton() {
        ReporterExtensions.log("Click third item radio button");
        thirdItemRadioButton.click();

        return this;
    }

    @Step("Click submit button")
    public BillingOrderPage clickSubmitButton() {
        ReporterExtensions.log("Click submit button");
        submitButton.click();

        return this;
    }

    @Step("Submit order form")
    public BillingOrderPage sendOrder(BillingOrder order) throws Exception {
        fillFirstName(order.firstName);
        fillLastName(order.lastName);
        fillEmail(order.email);
        fillPhone(order.phone);
        fillAddressLine1(order.addressLine1);
        fillAddressLine2(order.addressLine2);
        fillCity(order.city);
        fillZipCode(order.zipCode);
        clickStateSelect();
        clickStateOption(order.state);

        switch (order.itemNumber) {
            case 0:
                break;
            case 1:
                clickFirstItemRadioButton();
                break;
            case 2:
                clickSecondItemRadioButton();
                break;
            case 3:
                clickThirdItemRadioButton();
                break;
            default:
                throw new Exception("Item number must be 1,2 or 3");
        }

        fillComment(order.comment);
        clickSubmitButton();

        return this;
    }

    @Step("Check success message displayed")
    public Boolean isSuccessMessageDisplayed(long timeout) {
        ReporterExtensions.log("Check success message displayed");

        return WebDriverExtensions.waitUntilElementIsDisplay(driver, By.xpath(SUCCESS_MESSAGE), timeout);
    }

    @Step("Total amount should be {expectedAmount}")
    public Boolean isExpectedTotalAmountDisplayed(String expectedAmount) {
        ReporterExtensions.log("Total amount should be " + expectedAmount);

        if (WebDriverExtensions.isTextPresentInElementLocated(driver, By.xpath(TOTAL_AMOUNT), expectedAmount)) {
            return true;
        } else {
            var actualText = totalAmountTextField.getText();
            ReporterExtensions.log("Actual result: " + actualText);
        }

        return false;
    }

    @Step("Check validation error messages displayed")
    public Boolean isRequiredFieldsValidationErrorsDisplayed() {
        ArrayList<WebElement> requiredFields = new ArrayList<WebElement>()  {{
            add(firstNameInput);
            add(lastNameInput);
            add(emailInput);
            add(phoneInput);
            add(addressLine1Input);
            add(cityInput);
            add(zipCodeInput);
            add(firstItemRadioButton);
            add(secondItemRadioButton);
            add(thirdItemRadioButton);
            add(commentInput);
        }};

        WebDriverExtensions.waitUntilElementIsDisplay(driver, By.xpath(VALIDATION_ERRORS),3);

        for(var field : requiredFields) {
            WebElementExtensions.scrollToElement(driver, field);

            if (!field.getAttribute("class").contains("wpforms-error")) {
                ReporterExtensions.log("There is no validation message in field - " + field.getAttribute("name"));
                return false;
            }
        }

        return true;
    }

    //Webelement Object Repository
    @FindBy(name = "wpforms[fields][0][first]")
    WebElement firstNameInput;

    @FindBy(name = "wpforms[fields][0][last]")
    WebElement lastNameInput;

    @FindBy(name = "wpforms[fields][1]")
    WebElement emailInput;

    @FindBy(name = "wpforms[fields][2]")
    WebElement phoneInput;

    @FindBy(name = "wpforms[fields][3][address1]")
    WebElement addressLine1Input;

    @FindBy(name = "wpforms[fields][3][address2]")
    WebElement addressLine2Input;

    @FindBy(name = "wpforms[fields][3][city]")
    WebElement cityInput;

    @FindBy(name = "wpforms[fields][3][postal]")
    WebElement zipCodeInput;

    @FindBy(name = "wpforms[fields][3][state]")
    WebElement stateSelect;

    WebElement stateOption;

    @FindBy(name = "wpforms[fields][6]")
    WebElement commentInput;

    @FindBy(xpath = FIRST_ITEM)
    WebElement firstItemRadioButton;

    @FindBy(xpath = SECOND_ITEM)
    WebElement secondItemRadioButton;

    @FindBy(xpath = THIRD_ITEM)
    WebElement thirdItemRadioButton;

    @FindBy(name = "wpforms[submit]")
    WebElement submitButton;

    @FindBy(xpath = TOTAL_AMOUNT)
    WebElement totalAmountTextField;

    static final String STATE_OPTION = "//option[@value='{code}']";
    static final String FIRST_ITEM = "//input[@data-amount=\"10.00\"]";
    static final String SECOND_ITEM = "//input[@data-amount=\"20.00\"]";
    static final String THIRD_ITEM = "//input[@data-amount=\"30.00\"]";
    static final String TOTAL_AMOUNT = "//div[@class='wpforms-payment-total']";

    static final String SUCCESS_MESSAGE = "//p[text()='Thanks for contacting us! We will be in touch with you shortly.']";
    static final String VALIDATION_ERRORS = "//*[contains(@class, 'wpforms-error')]";
}
