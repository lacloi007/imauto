package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;

@Component(value = "set")
public class ACSet extends Action {

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// get input from arguments
		String value = args.length >= 3 ? args[2] : StringUtils.EMPTY;
		value = replaceParam(data, value);
		value = process(value);

		// set value to data
		data.put(args[1], replaceParam(data, value));

	}

	private String process(String input) {
		String result = input;
		try {
			if (input.contains("+") || input.contains("-")) {
				ScriptEngineManager mgr = new ScriptEngineManager();
				ScriptEngine engine = mgr.getEngineByName("JavaScript");
				int intValue = (Integer) engine.eval(input);
				result = intValue + "";
			}
		} catch (Exception e) {
			result = input;
		}

		return result;
	}
}
