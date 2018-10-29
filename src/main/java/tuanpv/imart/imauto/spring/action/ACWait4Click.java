package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.element.EMClick;

@Component(value = ACWait4Click.NAME)
public class ACWait4Click extends Action {
	public static final String NAME = "wait4Click";

	@Autowired
	private ACWait wait;

	@Autowired
	private EMClick click;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// call wait
		wait.execute(data, new String[] { ACWait.NAME, args[1], "clickable" });

		// call text
		click.execute(data, new String[] { EMClick.NAME, args[1] });
	}
}
