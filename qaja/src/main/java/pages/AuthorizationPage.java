package pages;

import extensions.ReporterExtensions;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AuthorizationPage extends BasePage {
    
    public AuthorizationPage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Open authorization page - {url}")
    public AuthorizationPage goToPage(String url) {
        ReporterExtensions.log("Open authorization page");
        driver.navigate().to(url);

        driver.manage().window().maximize();
        return this;
    }
    
    @Step("Log in")
    public BillingOrderPage logIn(String password) {
        fillPassword(password);

        return clickSubmitButton();
    }

    @Step("Fill password - {password}")
    public AuthorizationPage fillPassword(String password) {
        ReporterExtensions.log(String.format("Fill password '%s'", password));
        PasswordInput.sendKeys(password);

        return this;
    }

    @Step("Click submit button")
    public BillingOrderPage clickSubmitButton() {
        ReporterExtensions.log("Click submit button");
        SubmitButton.click();

        return new BillingOrderPage(driver);
    }

    //Webelement Initializtion
    @FindBy(name = "wpforms[form_locker_password]")
    WebElement PasswordInput;

    @FindBy(name = "wpforms[submit]")
    WebElement SubmitButton;    
}
