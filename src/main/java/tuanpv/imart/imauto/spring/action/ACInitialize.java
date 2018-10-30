package tuanpv.imart.imauto.spring.action;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;

@Component(value = "initialize")
public class ACInitialize extends Action {

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {
		Date today = new Date();
		data.put("id.desc", today.getTime() + "");
		data.put("id.uuid", uuid());

		// log
		System.out.printf(" + id.desc : %s\n + id.uuid : %s\n\n");
	}
}
