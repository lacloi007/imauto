package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;

@Component(value = "sleep")
public class ACSleep extends Action {

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// get input from arguments
		String value = replaceParam(data, args[1]) ;
		Thread.sleep(Integer.parseInt(value));
	}
}
