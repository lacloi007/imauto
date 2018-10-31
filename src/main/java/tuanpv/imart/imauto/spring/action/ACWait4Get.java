package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.Constant;
import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = ACWait4Get.NAME)
public class ACWait4Get extends Action {
	public static final String NAME = "wait4Get";

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
		WebElement element = waitBy(By.xpath(xpath), type);
		data.put(Constant.KEY_ELM_PREVIOUS, element);
	}
}
