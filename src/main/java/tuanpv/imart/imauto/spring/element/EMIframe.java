package tuanpv.imart.imauto.spring.element;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = EMIframe.NAME)
public class EMIframe extends Action {
	public static final String NAME = "iframe";

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize
		init(imConfig);

		// parse input
		String xpath = replaceParam(data, args[1]);

		// process
		WebElement iframe = waitBy(By.xpath(xpath));
		driver.switchTo().frame(iframe);
	}
}
