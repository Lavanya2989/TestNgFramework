package extensions;

import io.qameta.allure.Attachment;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class WebDriverExtensions {

    public static long defaultTimeout = 10;
    public static long implicitWait = 3;
    public static long noWait = 0;

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static void executeScript(WebDriver driver, String script) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor)driver).executeScript(script);
        } else {
            throw new IllegalStateException("This driver does not support JavaScript!");
        }
    }

    public static Boolean waitUntilElementIsDisplay(WebDriver driver, By by, long timeout) {
        var wait = new WebDriverWait(driver, timeout);

        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by)) != null;
        } catch (TimeoutException e) {
            return false;
        } catch (StaleElementReferenceException e) {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by)) != null;
        }
    }

    public static Boolean isTextPresentInElementLocated(WebDriver driver, By by, String text) {
        var wait = new WebDriverWait(driver, implicitWait);

        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public static Boolean waitUntilElementIsAppear(WebDriver driver, By by, long timeout) {
        var wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public static Boolean waitUntilElementIsDisappeared(WebDriver driver, By by, long timeout) {
        var wait = new WebDriverWait(driver, timeout);
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public static void closeAlertIfExist(WebDriver driver) {
        var alert = driver.switchTo().alert();

        ReporterExtensions.log("Alert text: "+ alert.getText());
        ReporterExtensions.log("Click Cancel");
        alert.dismiss();
    }

    public static void sendHotKeys(WebDriver driver, String key, Boolean control, Boolean shift, Boolean alt) throws InterruptedException {
        var keysCombinationDescriptor = new StringBuilder();
        if (control) { keysCombinationDescriptor.append("Ctrl+"); }
        if (alt) { keysCombinationDescriptor.append("Alt+"); }
        if (shift) { keysCombinationDescriptor.append("Shift+"); }

        keysCombinationDescriptor.append(key);
        ReporterExtensions.log("Press " + keysCombinationDescriptor);
        var actions = new Actions(driver);

        if (control) { actions.keyDown(Keys.CONTROL); }
        if (shift) { actions.keyDown(Keys.SHIFT); }
        if (alt) { actions.keyDown(Keys.ALT); }

        Thread.sleep(500);
        actions.sendKeys(key);

        if (control) { actions.keyUp(Keys.CONTROL); }
        if (shift) { actions.keyUp(Keys.SHIFT); }
        if (alt) { actions.keyUp(Keys.ALT); }

        actions.build().perform();
    }
}
