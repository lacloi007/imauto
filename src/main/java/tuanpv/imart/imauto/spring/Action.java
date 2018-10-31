package tuanpv.imart.imauto.spring;

import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.text.StrSubstitutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import tuanpv.imart.imauto.Constant;
import tuanpv.imart.imauto.extension.ExtensionConditions;
import tuanpv.imart.imauto.spring.system.IMConfig;

public abstract class Action {
	// define constant
	public static final int CLICKABLE = 1;
	public static final int VISIBLE = 2;

	// define public object
	public WebDriver driver;
	public WebDriverWait wait;
	public Map<String, Object> config;
	public Map<String, String> common, driverConfig;
	public String[] tests;

	public void init(IMConfig imConfig) {
		this.driver = imConfig.driver;
		this.wait = imConfig.wait;
		this.common = imConfig.common;
		this.driverConfig = imConfig.driverConfig;
		this.config = imConfig.config;
		this.tests = imConfig.tests;
	}

	public WebElement waitBy(By by, int type) throws Exception {
		switch (type) {
		case CLICKABLE:
			return wait.until(ExtensionConditions.elementToBeClickable(by));

		default:
			return wait.until(ExtensionConditions.visibilityOfElementLocated(by));
		}
	}

	public WebElement findElement(Map<String, Object> data, String xpath) throws Exception {
		WebElement element = null;
		if (data.containsKey(Constant.KEY_ELM_PREVIOUS)) {
			element = (WebElement) data.get(Constant.KEY_ELM_PREVIOUS);
			data.remove(Constant.KEY_ELM_PREVIOUS);
		} else {
			element = driver.findElement(By.xpath(xpath));
		}

		return element;
	}

	public WebElement waitBy(By by) throws Exception {
		return waitBy(by, VISIBLE);
	}

	public void waitLogo() throws Exception {
		waitBy(By.className(common.get("logo-class-name").toString()), VISIBLE);
	}

	public static String replaceParam(Map<String, Object> data, String template) {
		StrSubstitutor sub = new StrSubstitutor(data, "${", "}");
		return sub.replace(template);
	}

	public String parseArgs(String[] args, int idx, String def) {
		if (args.length > idx)
			return args[idx];
		return def;
	}

	public String uuid() {
		return UUID.randomUUID().toString();
	}

	public abstract void execute(Map<String, Object> data, String[] args) throws Exception;
}
