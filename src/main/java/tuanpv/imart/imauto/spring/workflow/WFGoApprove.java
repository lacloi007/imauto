package tuanpv.imart.imauto.spring.workflow;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.action.ACTakeScreenShot;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "wfGoApprove")
public class WFGoApprove extends Action {

	@Autowired
	private ACTakeScreenShot takeScreenShot;

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String description = replaceParam(data, args[1]);

		// goto page
		driver.get(common.get("wf-approve-url").toString());

		// enter user name
		waitBy(By.id("conditionGreyBox"), CLICKABLE).click();

		// goto Work flow window
		WebElement gbWindow = waitBy(By.className("GB_frame"));
		driver.switchTo().frame(gbWindow);
		driver.switchTo().frame(driver.findElement(By.id("GB_frame")));

		// enter Description for searching
		WebElement wfForm = driver.findElement(By.id("imui-tabitem-tab_searchInfo"));
		WebElement wfElem = wfForm.findElement(By.id("listPageCol_MatterName"));
		wfElem.clear();
		wfElem.sendKeys(description);

		// click search
		wfForm.findElement(By.id("search")).click();

		// goto first item found
		WebElement lnkProcess = waitBy(By.xpath("//tr[@id='1']/td[1]/a"));

		// take screen shot
		takeScreenShot.execute(data, new String[] { ACTakeScreenShot.NAME });

		// click to forward to Process screen
		lnkProcess.click();
	}
}
