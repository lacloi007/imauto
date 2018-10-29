package tuanpv.imart.imauto.spring.workflow;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.action.ACTakeScreenShot;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "wfOpenDetail")
public class WFOpenDetail extends Action {

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
		driver.get(common.get("wf-process-url").toString());

		// click to COMPLETED
		waitBy(By.xpath("//a[@href='#imui-tabitem-tab_complete_list']"), CLICKABLE).click();

		// goto search page
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
		WebElement lnkDetail = waitBy(By.xpath("//tr[@id='1']/td[@aria-describedby='completeList_listPageCol_Detail']/a"));

		// take screen shot
		takeScreenShot.execute(data, new String[] { ACTakeScreenShot.NAME });

		// click to forward to Process screen
		lnkDetail.click();

		// working with new window
		String currentWindow = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equalsIgnoreCase(currentWindow)) {
				int timeout = Integer.parseInt(driverConfig.get("timeout").toString());
				int width = Integer.parseInt(driverConfig.get("width").toString());
				int height = Integer.parseInt(driverConfig.get("height").toString());

				driver.switchTo().window(handle);

				// create Options
				Options options = driver.manage();
				options.timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
				options.window().setSize(new Dimension(width, height));
				break;
			}
		}
	}
}
