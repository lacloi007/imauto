package tuanpv.imart.imauto.spring.element;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.extension.ExtensionConditions;
import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = EMAutocomplete.NAME)
public class EMAutocomplete extends Action {
	public static final String NAME = "autocomplete";

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String xpath = replaceParam(data, args[1]);
		String code = replaceParam(data, args[2]);

		// goto login page
		WebElement select = driver.findElement(By.xpath(xpath));
		WebElement parent = select.findElement(By.xpath("./.."));
		WebElement span = parent.findElement(By.xpath("span"));
		span.click();

		WebElement input = wait.until(ExtensionConditions.visibilityOfElementLocated(By.xpath("//input[@type='search' and @role='textbox']")));
		input.sendKeys(code);
		input.sendKeys(Keys.ENTER);
	}
}
