package tuanpv.imart.imauto.extension;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * The extension conditions for waiting action
 * 
 * @author TuanPV
 */
public class ExtensionConditions {
	private final static Logger log = Logger.getLogger(ExpectedConditions.class.getName());

	private ExtensionConditions() {
	}

	public static ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				try {
					return elementIfVisible(findElement(locator, driver));
				} catch (StaleElementReferenceException e) {
					return null;
				}
			}

			@Override
			public String toString() {
				return "visibility of element located by " + locator;
			}
		};
	}

	public static ExpectedCondition<WebElement> elementToBeClickable(final By locator) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				WebElement element = visibilityOfElementLocated(locator).apply(driver);
				try {
					if (element != null && element.isEnabled()) {
						return element;
					}
					return null;
				} catch (StaleElementReferenceException e) {
					return null;
				}
			}

			@Override
			public String toString() {
				return "element to be clickable: " + locator;
			}
		};
	}

	public static ExpectedCondition<WebElement> elementToBeClickable(final WebElement element) {
		return new ExpectedCondition<WebElement>() {

			@Override
			public WebElement apply(WebDriver driver) {
				WebElement visibleElement = visibilityOf(element).apply(driver);
				try {
					if (visibleElement != null && visibleElement.isEnabled()) {
						return visibleElement;
					}
					return null;
				} catch (StaleElementReferenceException e) {
					return null;
				}
			}

			@Override
			public String toString() {
				return "element to be clickable: " + element;
			}
		};
	}

	public static ExpectedCondition<WebElement> visibilityOf(final WebElement element) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				return elementIfVisible(element);
			}

			@Override
			public String toString() {
				return "visibility of " + element;
			}
		};
	}

	private static WebElement elementIfVisible(WebElement element) {
		return element.isDisplayed() ? element : null;
	}

	private static WebElement findElement(By by, WebDriver driver) {
		try {
			return driver.findElements(by).stream().filter(x -> x.isDisplayed()).findFirst().orElseThrow(() -> new NoSuchElementException("Cannot locate an element using " + by));
		} catch (NoSuchElementException e) {
			throw e;
		} catch (WebDriverException e) {
			log.log(Level.WARNING, String.format("WebDriverException thrown by findElement(%s)", by), e);
			throw e;
		}
	}

	public static ExpectedCondition<String> visibilityOfOneOfTwoLocator(final By byA, final By byB) {
		return new ExpectedCondition<String>() {
			@Override
			public String apply(WebDriver driver) {
				try {
					WebElement elA = driver.findElement(byA);
					if (elA != null && elA.isDisplayed())
						return "1";

				} catch (Exception e) { }

				try {
					WebElement elB = driver.findElement(byB);
					if (elB != null && elB.isDisplayed())
						return "2";
				} catch (Exception e) { }
				return null;
			}

			@Override
			public String toString() {
				return "visibility of one of two " + byA + ", " + byB;
			}
		};
	}

	public static ExpectedCondition<WebElement> visibilityOfOneOfTwoElements(final WebElement elementA, final WebElement elementB) {
		return new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				if (elementA.isDisplayed())
					return elementA;
				if (elementB.isDisplayed())
					return elementB;
				return null;
			}

			@Override
			public String toString() {
				return "visibility of one of two " + elementA + ", " + elementB;
			}
		};
	}
}
