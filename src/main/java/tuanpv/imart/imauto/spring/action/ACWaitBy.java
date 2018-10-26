package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "wait")
public class ACWaitBy extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String xpath = args[1];
		String val = args.length >= 3 ? args[2].toLowerCase().trim() : "visible";

		// default is visible
		int type = VISIBLE;
		if (val.equals("clickable"))
			type = CLICKABLE;

		// wait by
		waitBy(By.xpath(xpath), type);
	}
}
