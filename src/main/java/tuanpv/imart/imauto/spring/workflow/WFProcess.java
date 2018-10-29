package tuanpv.imart.imauto.spring.workflow;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.action.ACTakeScreenShot;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "wfProcess")
public class WFProcess extends Action {

	@Autowired
	private ACTakeScreenShot takeScreenShot;

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String xpath = replaceParam(data, args[1]);
		String type = replaceParam(data, args[2]);

		// click to Process button
		waitBy(By.xpath(xpath), CLICKABLE).click();

		// switch to frame
		WebElement gbWindow = waitBy(By.className("GB_frame"));
		driver.switchTo().frame(gbWindow);
		driver.switchTo().frame(driver.findElement(By.id("GB_frame")));
		driver.switchTo().frame(driver.findElement(By.id("IMW_PROC_MAIN")));

		// select type of processing
		Select dropdown = new Select(driver.findElement(By.xpath("//select[@name='processType']")));
		dropdown.selectByVisibleText(type);

		// wait for button is updated
		WebElement button = waitBy(By.xpath(String.format("//input[@id='proc_button' and @value='%s']", type)), CLICKABLE);

		// take screen shot
		takeScreenShot.execute(data, new String[] { ACTakeScreenShot.NAME });

		// click to button
		button.click();

		// confirm
		waitBy(By.xpath("//div[contains(@class, 'ui-dialog-buttonset')]/button[1]"), CLICKABLE).click();

		// wait for finish
		waitLogo();

		// take screen shot
		takeScreenShot.execute(data, new String[] { ACTakeScreenShot.NAME });
	}
}
