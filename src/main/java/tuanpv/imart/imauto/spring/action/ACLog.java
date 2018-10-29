package tuanpv.imart.imauto.spring.action;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.system.IMConfig;

@Component(value = ACLog.NAME)
public class ACLog extends Action {
	public static final String NAME = "log";

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
		String[] paths = new String[] { common.get("screen-shot-path").toString(), data.get("test-name").toString(), ((Date) data.get("test-time")).getTime() + ".txt" };
		String filePath = StringUtils.join(paths, File.separator);

		// get input from arguments
		String value = replaceParam(data, args[1]) + "\n\n";

		try {
			FileUtils.writeStringToFile(new File(filePath), value, "UTF-8", true);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}