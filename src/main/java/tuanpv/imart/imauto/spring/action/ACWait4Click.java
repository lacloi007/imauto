package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = ACWait4Click.NAME)
public class ACWait4Click extends Action {
	public static final String NAME = "wait4Click";

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String xpath = replaceParam(data, args[1]);

		// goto login page
		waitBy(By.xpath(xpath), CLICKABLE).click();
	}
}
