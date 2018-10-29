package tuanpv.imart.imauto.spring.element;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "uploadByVue")
public class EMUploadByVue extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String xpath = replaceParam(data, args[1]);
		String value = replaceParam(data, args[2]);

		// set XPath for sub component
		String xpathFile = String.format("%s//input[@type='file']", xpath);
		String xpathList = String.format("%s//table[@id='attachfile-file-list-table']", xpath);

		// goto login page
		if (StringUtils.isNotEmpty(value)) {
			File file = new File(value);
			if (file.exists() && file.isFile()) {
				WebElement upload = driver.findElement(By.xpath(xpathFile));
				upload.sendKeys(value);

				// wait for uploading finish
				waitBy(By.xpath(xpathList));
			}
		}
	}
}
