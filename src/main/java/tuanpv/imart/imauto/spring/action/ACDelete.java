package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;

@Component(value = "delete")
public class ACDelete extends Action {

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// get input from arguments
		String key = replaceParam(data, args[1]);

		// remove key from TEST-DATA
		if (data.containsKey(key))
			data.remove(key);
	}
}
