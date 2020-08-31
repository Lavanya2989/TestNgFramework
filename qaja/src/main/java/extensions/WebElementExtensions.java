package extensions;

import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public final class WebElementExtensions {
    public static void scrollToElement(WebDriver driver, WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.perform();
    }

    public static void hoverElement(WebDriver driver, WebElement webElement) {
        try {
            var actionBuilder = new Actions(driver);
            actionBuilder.moveToElement(webElement).build().perform();
        } catch (StaleElementReferenceException e) {
            ReporterExtensions.log("StaleElementReferenceException: HoverElement.");
            var actionBuilder = new Actions(driver);
            actionBuilder.moveToElement(webElement).build().perform();
        }
    }

    public static void doubleClick(WebDriver driver, WebElement webElement) {
        try {
            var action = new Actions(driver);
            action.doubleClick(webElement);
            action.perform();
        } catch (StaleElementReferenceException e) {
            ReporterExtensions.log("StaleElementReferenceException: DoubleClickElement: " + webElement.getTagName());
            var action = new Actions(driver);
            action.doubleClick(webElement);
            action.perform();
        }
    }

    public static void clearByHotkeys(WebDriver driver, WebElement webElement) throws InterruptedException {
        var actions = new Actions(driver);

        doubleClick(driver, webElement);
        Thread.sleep(100);

        actions.keyDown(Keys.CONTROL);
        actions.sendKeys("a");
        actions.keyUp(Keys.CONTROL);
        actions.sendKeys(Keys.BACK_SPACE);
        actions.build().perform();
    }
}
