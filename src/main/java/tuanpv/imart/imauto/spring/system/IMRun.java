package tuanpv.imart.imauto.spring.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.action.ACLog;

@Component(value = "imRun")
public class IMRun extends Action {

	@Autowired
	private ACLog log;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private IMConfig imConfig;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// initialize
		init(imConfig);

		// bypass in case tests is empty
		if (tests == null || tests.length == 0)
			return;

		// process all test
		for (String testName : tests) {
			Date today = new Date();

			// TODO: initialize test data
			Map<String, Object> testData = new TreeMap<>();
			testData.put("test-time", today);
			testData.put("test-time-long", today.getTime() + "");
			testData.put("uuid", genUUID());

			// run test case
			List<String[]> steps = getSteps(testName);
			for (String[] step : steps) {
				String actCode = step[0];

				try {
					// run in case ACTION-CODE is defined in system
					if (context.containsBean(actCode)) {
						Action action = (Action) context.getBean(actCode);
						action.execute(testData, step);
						continue;
					}

					// run in case ACTION-CODE is defined from EXCEL Input
					if (config.containsKey(actCode)) {
						final String argumentTemplate = "args[%d]";

						// add argument to TEST-DATA
						for (int idx = 1; idx < step.length; idx++) {
							testData.put(String.format(argumentTemplate, idx - 1), step[idx]);
						}

						// run custom command
						List<String[]> cmdSteps = getSteps(actCode);
						for (String[] cmdStep : cmdSteps) {
							String cmdCode = cmdStep[0];
							if (context.containsBean(cmdCode)) {
								Action action = (Action) context.getBean(cmdCode);
								action.execute(testData, cmdStep);
							}
						}

						// delete argument from TEST-DATA
						for (int idx = 1; idx < step.length; idx++) {
							testData.remove(String.format(argumentTemplate, idx - 1));
						}

						continue;
					}
				} catch (Exception exception) {
					String content = String.format("=> Exception:%s\n + %s\n\n", exception.getMessage(), exception.getLocalizedMessage());
					log.execute(testData, new String[] { ACLog.NAME, content });
					throw exception;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<String[]> getSteps(String sheetName) {
		Map<String, Object> testCase = (Map<String, Object>) config.get(sheetName);
		return (List<String[]>) testCase.get("object");
	}

	private String genUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
