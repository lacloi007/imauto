package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "login")
public class ACLogin extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String user = args[1];
		String pass = args.length >= 3 ? args[2] : StringUtils.EMPTY;

		// goto login page
		driver.get(common.get("log-in-url").toString());

		// enter user name
		WebElement element = waitBy(By.id(common.get("log-in-uid-id").toString()));
		element.clear();
		element.sendKeys(user);

		// enter password
		element = driver.findElement(By.id(common.get("log-in-pwd-id").toString()));
		element.clear();
		element.sendKeys(pass);

		// click login
		driver.findElement(By.className(common.get("log-in-btn-class-name").toString())).click();

		// wait action finish
		waitLogo();
	}
}
