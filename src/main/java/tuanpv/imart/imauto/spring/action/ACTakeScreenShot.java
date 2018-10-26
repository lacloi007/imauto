package tuanpv.imart.imauto.spring.action;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = "takeScreenShot")
public class ACTakeScreenShot extends Action {

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize variable
		init(imConfig);

		// RETURN when data do not have enough key
		if (!data.containsKey("test-name") || !data.containsKey("test-part") || !data.containsKey("test-time"))
			return;

		// create path
		String[] paths = new String[] { common.get("screen-shot-path").toString(), data.get("test-name").toString(), ((Date) data.get("test-time")).getTime() + "" };

		// create directory for screen shot
		String path = StringUtils.join(paths, File.separator);
		FileUtils.forceMkdir(new File(path));

		// create JavascriptExecutor
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		// get window height
		Integer dfWidth = Integer.parseInt(driverConfig.get("width"));
		// System.out.printf(" default screen   [%6d, %6d]\n", dfWidth, dfHeight);

		// scroll height
		Integer brHeight = ((Long) executor.executeScript("return document.body.scrollHeight")).intValue();
		// System.out.printf(" scroll size      [%6d, %6d]\n", brWidth, brHeight);
		Integer position = 0;

		// image default
		int actualWidth = 0, actualHeight = 0;

		BufferedImage screenShot = new BufferedImage(dfWidth, brHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = screenShot.createGraphics();
		do {
			// scroll to position
			executor.executeScript(String.format("window.scrollTo(0, %d)", position));

			// take screen shoot
			BufferedImage image = ImageIO.read(((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE));
			if (position == 0) {
				// System.out.printf(" screen shot part [%6d, %6d]\n", image.getWidth(), image.getHeight());
				actualWidth = image.getWidth();
				actualHeight = image.getHeight();
			}

			// process screen shot file
			if (position < brHeight && position + actualHeight > brHeight) {
				// crop buffer image
				int imgHeigh = brHeight - position;
				int imgTop = actualHeight - imgHeigh;
				image = image.getSubimage(0, imgTop, actualWidth, imgHeigh);
			}

			// join image to screen shot
			g2d.drawImage(image, 0, position, null);

			// increase position
			position += actualHeight;
		} while (position < brHeight);

		// dispose graphic
		g2d.dispose();

		// get file name
		int index = 1;
		String fileName;
		do {
			fileName = String.format("%s-%05d.jpg", data.get("test-part"), index++);
		} while ((new File(path + File.separator + fileName)).exists());

		// write to file
		ImageIO.write(screenShot, "jpg", new File(path + File.separator + fileName));
	}
}
