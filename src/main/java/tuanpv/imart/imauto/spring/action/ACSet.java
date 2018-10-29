package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;

@Component(value = "set")
public class ACSet extends Action {

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// get input from arguments
		String value = args.length >= 3 ? args[2] : StringUtils.EMPTY;

		// set value to data
		data.put(args[1], replaceParam(data, value));
	}
}
