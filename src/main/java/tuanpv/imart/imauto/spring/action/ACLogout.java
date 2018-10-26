package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "logout")
public class ACLogout extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// logout from system
		driver.get(common.get("log-out-url").toString());
		waitBy(By.className(common.get("log-in-logo-class-name").toString()));
	}
}
