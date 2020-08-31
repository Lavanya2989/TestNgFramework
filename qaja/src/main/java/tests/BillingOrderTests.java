package tests;

import com.automation.remarks.video.annotations.Video;
import dictionaries.State;
import models.BillingOrder;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AuthorizationPage;
import pages.BillingOrderPage;

public class BillingOrderTests extends BaseTest {

    @BeforeMethod
    public void SetUp() {
        var authorizationPage = new AuthorizationPage(driver);

        authorizationPage
            .goToPage("http://qaauto.co.nz/billing-order-form/")
            .logIn("Testing");
    }

    @Video
    @Test(description = "Submit billing order with all required parameters", dataProvider = "provideCorrectOrders")
    public void SubmitFormWithAllParametersTest(BillingOrder order) throws Exception {
        var billingOrderPage = new BillingOrderPage(driver);

        billingOrderPage.sendOrder(order);

        Assert.assertTrue(billingOrderPage.isSuccessMessageDisplayed(10));
    }

    @Video
    @Test(description = "Total amount visibility test")
    public void TotalAmountTest() {
        var billingOrderPage = new BillingOrderPage(driver);
        var firstItemAmount = "$ 10.00";
        var secondItemAmount = "$ 20.00";
        var thirdItemAmount = "$ 30.00";

        billingOrderPage.clickFirstItemRadioButton();

        var result1 = billingOrderPage.isExpectedTotalAmountDisplayed(firstItemAmount);

        billingOrderPage.clickSecondItemRadioButton();

        var result2 = billingOrderPage.isExpectedTotalAmountDisplayed(secondItemAmount);

        billingOrderPage.clickThirdItemRadioButton();

        var result3 = billingOrderPage.isExpectedTotalAmountDisplayed(thirdItemAmount);

        Assert.assertTrue(result1, "Incorrect total amount for first item");
        Assert.assertTrue(result2, "Incorrect total amount for second item");
        Assert.assertTrue(result3, "Incorrect total amount for third item");
    }

    @Video
    @Test(description = "Required fields validation test")
    public void RequiredFieldsTest() {
        var billingOrderPage = new BillingOrderPage(driver);

        billingOrderPage.clickSubmitButton();

        Assert.assertTrue(billingOrderPage.isRequiredFieldsValidationErrorsDisplayed());
    }

    @DataProvider(name = "provideCorrectOrders")
    public Object[][] provideData() {
        return new Object[][] {
                { new BillingOrder("John", "Smith", "mail@email.com","88002000500","Kanzas","54321", State.AK,"State","District", 1, "Comment") },
                { new BillingOrder("John", "Li", "mail@email.com","88002000500","Kanzas","54321", State.AK,"State","District", 1, "Comment") },
                { new BillingOrder("John", "Smith-Li", "mail@email.com","88002000500","Kanzas","54321", State.AK,"State","District", 1, "Comment") },
                { new BillingOrder("John", "Smith-Li O'Connor", "mail@email.com","88002000500","Kanzas","54321", State.AK,"State","District", 1, "Comment") }
        };
    }
}
