package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;

@Component(value = "deleteArr")
public class ACDeleteArr extends Action {

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// get input from arguments
		String objCode = replaceParam(data, args[1]);
		String format = "%s[%d]";

		// remove
		int x = 0;
		while (data.containsKey(String.format(format, objCode, x))) {
			data.remove(String.format(format, objCode, x++));
		}
	}
}
