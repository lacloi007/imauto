package tuanpv.imart.imauto.spring.js;

import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "jsClick")
public class JSClick extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String id = replaceParam(data, args[1]);
		String script = String.format("return document.getElementById('%s').click();", id);

		// create JavascriptExecutor
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript(script);
	}
}
