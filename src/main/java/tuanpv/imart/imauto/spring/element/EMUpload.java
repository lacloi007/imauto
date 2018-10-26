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

@Component(value = "upload")
public class EMUpload extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// get input from arguments
		String xpath = args[1];
		String value = args.length >= 3 ? args[2] : StringUtils.EMPTY;

		// goto login page
		if (StringUtils.isNotEmpty(value)) {
			File file = new File(value);
			if (file.exists() && file.isFile()) {
				WebElement upload = driver.findElement(By.xpath(xpath));
				upload.sendKeys(value);

				Thread.sleep(10000);
			}
		}
	}
}
