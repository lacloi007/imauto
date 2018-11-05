package tuanpv.imart.imauto.spring.element;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "switchTo")
public class EMSwitchTo extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// working with new window
		String currentWindow = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equalsIgnoreCase(currentWindow)) {
				int timeout = Integer.parseInt(driverConfig.get("timeout").toString());
				int width = Integer.parseInt(driverConfig.get("width").toString());
				int height = Integer.parseInt(driverConfig.get("height").toString());

				driver.switchTo().window(handle);

				// create Options
				Options options = driver.manage();
				options.timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
				options.window().setSize(new Dimension(width, height));
				break;
			}
		}
	}
}
