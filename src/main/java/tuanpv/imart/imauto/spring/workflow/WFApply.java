package tuanpv.imart.imauto.spring.workflow;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "wfApply")
public class WFApply extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String flowName = replaceParam(data, args[1]);

		// goto login page
		driver.get(common.get("wf-apply-url").toString());

		// enter user name
		waitBy(By.id("conditionGreyBox"), CLICKABLE).click();

		// goto IFrame
		WebElement gbWindow = waitBy(By.className("GB_frame"));
		driver.switchTo().frame(gbWindow);
		driver.switchTo().frame(driver.findElement(By.id("GB_frame")));

		// wait to input element for search flow
		WebElement element = waitBy(By.id("listPageCol_FlowName"));
		element.clear();
		element.sendKeys(flowName);

		// click search
		driver.findElement(By.xpath("//div[contains(@class, 'imui-operation-parts')]/input[@type='button' and @value='Search']")).click();

		// goto first item found
		WebElement lnkApply = waitBy(By.xpath("//tr[@id='1']/td[1]/a"));
		lnkApply.click();
	}
}
