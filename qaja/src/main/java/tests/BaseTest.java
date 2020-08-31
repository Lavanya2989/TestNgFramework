package tests;

import com.automation.remarks.testng.UniversalVideoListener;
import extensions.WebDriverExtensions;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import java.io.IOException;
import org.testng.annotations.Listeners;

@Listeners(UniversalVideoListener.class)
public class BaseTest {
   
public static WebDriver driver;

    @Parameters("browser")
    @BeforeClass
    public void OneTimeSetUp(String browser) {
    	if (browser.equalsIgnoreCase("chrome")){
            //System.setProperty("webdriver.chrome.driver","C:\\chromedriver\\chromedriver.exe");
    		WebDriverManager.chromedriver().setup();
          	driver = new ChromeDriver();
        }
        if (browser.equalsIgnoreCase("firefox")){
             WebDriverManager.firefoxdriver().setup();
             driver = new FirefoxDriver();
        }
        if (browser.equalsIgnoreCase("edge")){
             WebDriverManager.edgedriver().setup();
             driver = new EdgeDriver();
        }
    }

      
    @AfterMethod
    public void TearDown() {
        try {
            attachPerformedActionsLog();
            WebDriverExtensions.takeScreenshot(driver);
        } catch (Exception ex) {
            Reporter.log("Error occurring while taking screenshot " + ex.toString());
        }
    }

    @AfterClass
    public void OneTimeTearDown()
    {
        driver.quit();
    }

    @Attachment(value = "Console output", type = "text/plain")
    public byte[] attachPerformedActionsLog() throws IOException {
        var lines = Reporter.getOutput();
        String report = "";

        for (String line : lines) {
            report = report + line + "\n";
        }

        return report.getBytes();
    }
}
