package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = ACWait.NAME)
public class ACWait extends Action {
	public static final String NAME = "wait";

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String xpath = replaceParam(data, args[1]);
		String value = parseArgs(args, 2, StringUtils.EMPTY);
		value = replaceParam(data, value);

		// default is visible
		int type = value.equalsIgnoreCase("clickable") ? CLICKABLE : VISIBLE;

		// wait by
		waitBy(By.xpath(xpath), type);
	}
}
