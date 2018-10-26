package tuanpv.imart.imauto.spring.system;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

@Component(value = "imConfig")
public class IMConfig {
	public WebDriver driver;
	public WebDriverWait wait;
	public Map<String, Object> config;
	public Map<String, String> common, driverConfig;
	public String[] tests;

	public IMConfig() {
	}

	@SuppressWarnings("unchecked")
	public void start(Map<String, Object> config) throws MalformedURLException {
		if (config == null)
			return;

		Map<String, Object> temporary;

		// set data to configuration
		this.config = config;

		// parse common data
		if (config.containsKey("driver")) {
			temporary = (Map<String, Object>) config.get("driver");
			driverConfig = (Map<String, String>) temporary.get("object");
			initDriver(driverConfig);
		}

		// parse common data
		if (config.containsKey("common")) {
			temporary = (Map<String, Object>) config.get("common");
			common = (Map<String, String>) temporary.get("object");
		}

		// parse test data
		if (config.containsKey("test")) {
			temporary = (Map<String, Object>) config.get("test");
			tests = (String[]) temporary.get("object");
		}
	}

	public void destroy() {
		if (driver != null) {
			driver.quit();
		}
	}

	/**
	 * TODO : initialize Driver
	 * 
	 * @param configuration
	 * @throws MalformedURLException
	 */
	private void initDriver(Map<String, String> configuration) throws MalformedURLException {
		String type = configuration.get("type").trim();
		String url = configuration.get("url").trim();
		String browser = configuration.get("browser").trim();

		if (type.equalsIgnoreCase("remote")) {

			// initialize WebDriver
			if (browser.equalsIgnoreCase("chrome")) {
				driver = new RemoteWebDriver(new URL(url), DesiredCapabilities.chrome());
			}
		}

		if (type.equalsIgnoreCase("native")) {

			// initialize WebDriver
			if (browser.equalsIgnoreCase("chrome")) {
				// locate driver
				File fileDriver = new File("selenium/driver/chromedriver-v2.41-win32/chromedriver.exe");

				// add driver to system
				System.setProperty("webdriver.chrome.driver", fileDriver.getAbsolutePath());

				// create CHROME option
				ChromeOptions options = new ChromeOptions();
				if (configuration.containsKey("headless") && configuration.get("headless").trim().equalsIgnoreCase("true")) {
					// create option headless
					options.setHeadless(true);
				}

				// create driver
				driver = new ChromeDriver(options);
			}
		}

		// initialize options for driver
		if (driver != null) {
			int timeout = Integer.parseInt(configuration.get("timeout").toString());
			int width = Integer.parseInt(configuration.get("width").toString());
			int height = Integer.parseInt(configuration.get("height").toString());

			// create Options
			Options options = driver.manage();
			options.timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
			options.window().setSize(new Dimension(width, height));

			// initialize WebDriverWait
			int waiter = Integer.parseInt(configuration.get("wait").toString());

			// create waiter
			wait = new WebDriverWait(driver, waiter);
		}
	}
}
