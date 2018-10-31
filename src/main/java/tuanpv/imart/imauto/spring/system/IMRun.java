package tuanpv.imart.imauto.spring.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.action.ACLog;

@Component(value = IMRun.NAME)
public class IMRun extends Action {
	public static final String NAME = "imRun";

	@Autowired
	private ACLog log;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private IMConfig imConfig;

	/**
	 * @throws Exception
	 */
	public void execute() throws Exception {
		execute(null, null);
	}

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
			testData.put("args", -1);

			try {
				run(testData, getSteps(testName));
			} catch (Exception exception) {
				String content = String.format("=> Exception:%s\n\n", exception.getMessage());
				log.execute(testData, new String[] { ACLog.NAME, content });
				throw exception;
			}
		}
	}

	private final String argsKeyIdx = "args";
	private final String argsArrTpl = "args(%d)";
	private final String argsItmTpl = "args[%d]";

	private void run(Map<String, Object> testData, List<String[]> steps) throws Exception {
		for (String[] step : steps) {
			String command = step[0];

			// run in case ACTION-CODE is defined in system
			if (context.containsBean(command)) {
				Action action = (Action) context.getBean(command);
				action.execute(testData, step);
				continue;
			}

			// run in case ACTION-CODE is defined from EXCEL Input
			if (config.containsKey(command)) {

				// define variable
				int argsIdx = Integer.parseInt(testData.get(argsKeyIdx).toString()) + 1;
				String argsKeyArr = String.format(argsArrTpl, argsIdx);
				String[] argsArr = new String[step.length - 1];

				// add argument to TEST-DATA
				for (int idx = 1; idx < step.length; idx++) {
					testData.put(String.format(argsItmTpl, idx - 1), step[idx]);
					argsArr[idx - 1] = step[idx];
				}

				// store argument to "ARGS(index)"
				testData.put(argsKeyIdx, argsIdx);
				testData.put(argsKeyArr, argsArr);

				// run custom command
				run(testData, getSteps(command));

				// delete current argument from TEST-DATA
				argsIdx = Integer.parseInt(testData.get(argsKeyIdx).toString());
				argsArr = (String[]) testData.get(String.format(argsArrTpl, argsIdx));
				for (int idx = 1; idx < argsArr.length; idx++) {
					testData.remove(String.format(argsItmTpl, idx - 1));
				}

				// reload argument from ARGS Storage
				argsIdx = argsIdx - 1;
				argsKeyArr = String.format(argsArrTpl, argsIdx);
				if (testData.containsKey(argsKeyArr)) {
					argsArr = (String[]) testData.get(argsKeyArr);
					for (int idx = 0; idx < argsArr.length; idx++) {
						testData.put(String.format(argsItmTpl, idx), argsArr[idx]);
					}
				}

				// update index of argument
				testData.put(argsKeyIdx, argsIdx);
				continue;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<String[]> getSteps(String sheetName) {
		Map<String, Object> testCase = (Map<String, Object>) config.get(sheetName);
		return (List<String[]>) testCase.get("object");
	}
}
