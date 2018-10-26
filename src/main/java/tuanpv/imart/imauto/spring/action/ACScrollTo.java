package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "scrollTo")
public class ACScrollTo extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {
		// initialize variable
		init(imConfig);

		// get input from arguments
		int position = Integer.parseInt(args[1]);

		// create JavascriptExecutor
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript(String.format("window.scrollTo(0, %d)", position));
	}
}
