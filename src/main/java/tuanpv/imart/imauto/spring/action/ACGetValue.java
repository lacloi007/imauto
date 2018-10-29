package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "getValue")
public class ACGetValue extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String xpath = replaceParam(data, args[1]);
		String key = replaceParam(data, args[2]);

		// set value to data
		String value = driver.findElement(By.xpath(xpath)).getAttribute("value");
		data.put(key, value);
	}
}
