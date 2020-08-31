package tests;

import com.automation.remarks.video.annotations.Video;
import helpers.CsvReader;
import models.Form;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AuthorizationPage;
import pages.TestBlankFormPage;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;

public class TestBlankFormPageTests extends BaseTest {

    @BeforeMethod
    public void SetUp() {
        var authorizationPage = new AuthorizationPage(driver);

        authorizationPage
                .goToPage("http://qaauto.co.nz/test-blank-form/")
                .logIn("Testing");
    }

    @Video
    @Test(description = "Submit form with all required parameters", dataProvider = "provideCorrectFormParameters")
    public void SubmitFormWithAllParametersTest(String firstName, String lastName, String email, String comment) throws Exception {
        var testBlankFormPage = new TestBlankFormPage(driver);

        testBlankFormPage.sendForm(firstName, lastName, email, comment);

        Assert.assertTrue(testBlankFormPage.isSuccessMessageDisplayed(10));
    }

    @Video
    @Test(description = "Submit form with all parameters from CSV file", dataProvider = "csvDataProvider")
    public void CsvExampleTest(String firstName, String lastName, String email, String comment) throws Exception {
        var form = new Form(firstName, lastName, email, comment);
        var testBlankFormPage = new TestBlankFormPage(driver);

        testBlankFormPage.sendForm(form);

        Assert.assertTrue(testBlankFormPage.isSuccessMessageDisplayed(10));
    }

    @DataProvider(name = "provideCorrectFormParameters")
    public Object[][] provideData() {
        return new Object[][] {
                { "John", "Doe", "email@gmail.com", "Comment" },
                { "John", "Doe", "email@gmail.com", "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTU+" },
                { "John", "Doe", "email@gmail.com", "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789+" },
                { "John", "Doe", "email@gmail.com", "~!@#$%^&*()_+{}|:\" <>?`-=[];',./~!@#$%^&*()_+{}|:\"<>?`-=[];',./ ~!@#$%^&*()_+{}|:\"<>?`-=[];',./~!@#$%X" }
        };
    }

    @DataProvider(name = "csvDataProvider")
    public Iterator<Object[]> testData() throws IOException
    {
        return CsvReader.parseCsvData(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\java\\testData\\forms.csv");
    }
}
