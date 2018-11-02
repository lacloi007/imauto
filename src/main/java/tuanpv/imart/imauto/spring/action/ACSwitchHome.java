package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "switchHome")
public class ACSwitchHome extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// working with new window
		String currentWindow = driver.getWindowHandle();

		// close PopUp
		driver.close();

		// switch to each other
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equalsIgnoreCase(currentWindow)) {
				driver.switchTo().window(handle);
				break;
			}
		}
	}
}
