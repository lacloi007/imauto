package tuanpv.imart.imauto.spring.workflow;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "wfSubmit")
public class WFSubmit extends Action {

	@Autowired
	private Action takeScreenShot;

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String xpath = args[1];
		String descEx = args[2];

		// create description
		String descValue = replaceParam(data, descEx);

		// click
		waitBy(By.xpath(xpath), CLICKABLE).click();
		WebElement gbWindow = waitBy(By.className("GB_frame"));
		driver.switchTo().frame(gbWindow);
		driver.switchTo().frame(driver.findElement(By.id("GB_frame")));
		driver.switchTo().frame(driver.findElement(By.id("IMW_PROC_MAIN")));

		// input description for applying
		WebElement wfForm = driver.findElement(By.id("allBlock"));
		WebElement wfDesc = wfForm.findElement(By.xpath("//input[@type='text' and @name='matterName']"));
		wfDesc.sendKeys(descValue);

		// take screen shot
		takeScreenShot.execute(data, null);

		// run apply
		driver.findElement(By.xpath(xpath)).click();
		waitBy(By.xpath("//div[contains(@class, 'ui-dialog-buttonset')]/button[1]"), CLICKABLE).click();

		// wait for finish
		waitLogo();

		// take screen shot
		takeScreenShot.execute(data, null);
	}
}
