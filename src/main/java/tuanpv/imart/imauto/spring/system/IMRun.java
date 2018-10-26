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

@Component(value = "imRun")
public class IMRun extends Action {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private IMConfig imConfig;

	@SuppressWarnings("unchecked")
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
			Map<String, Object> testCase = (Map<String, Object>) config.get(testName);
			List<String[]> steps = (List<String[]>) testCase.get("object");
			for (String[] step : steps) {
				String actCode = step[0];
				if (context.containsBean(actCode)) {
					Action action = (Action) context.getBean(actCode);
					action.execute(testData, step);
				}
			}
		}
	}

	private String genUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
