package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;

@Component(value = "parseArr")
public class ACParseArr extends Action {

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// get input from arguments
		String objCode = replaceParam(data, args[1]);
		String strArray = replaceParam(data, args[2]);
		String format = "%s[%d]";

		String[] array = strArray.split(",");
		for (int idx = 0; idx < array.length; idx++) {
			data.put(String.format(format, objCode, idx), array[idx]);
		}
	}
}
