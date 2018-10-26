package tuanpv.imart.imauto.spring;

import java.util.Map;

import org.apache.commons.lang.text.StrSubstitutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import tuanpv.imart.imauto.extension.ExtensionConditions;
import tuanpv.imart.imauto.spring.system.IMConfig;

public abstract class Action {
	public static final int CLICKABLE = 1;
	public static final int VISIBLE = 2;

	public WebDriver driver;
	public WebDriverWait wait;
	public Map<String, Object> config;
	public Map<String, String> common, driverConfig;;
	public String[] tests;

	// private data
	public Map<String, Object> props;

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

	public WebElement waitBy(By by) throws Exception {
		return waitBy(by, VISIBLE);
	}

	public void waitLogo() throws Exception {
		waitBy(By.className(common.get("logo-class-name").toString()), VISIBLE);
	}

	public String replaceParam(Map<String, Object> map, String template) {
		StrSubstitutor sub = new StrSubstitutor(map, "${", "}");
		return sub.replace(template);
	}

	public abstract void execute(Map<String, Object> data, String[] args) throws Exception;
}
