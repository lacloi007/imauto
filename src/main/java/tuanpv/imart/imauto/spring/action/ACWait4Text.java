package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "wait4Text")
public class ACWait4Text extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String xpath = args[1];
		String value = args[2] == null ? StringUtils.EMPTY : args[2].trim();
		value = replaceParam(data, value);

		// action
		WebElement element = waitBy(By.xpath(xpath));
		element.clear();
		element.sendKeys(value);
	}
}
