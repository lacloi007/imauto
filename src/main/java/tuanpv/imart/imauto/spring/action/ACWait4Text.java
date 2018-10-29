package tuanpv.imart.imauto.spring.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.Action;
import tuanpv.imart.imauto.spring.element.EMText;

@Component(value = ACWait4Text.NAME)
public class ACWait4Text extends Action {
	public static final String NAME = "wait4Text";

	@Autowired
	private ACWait wait;

	@Autowired
	private EMText text;

	@Override
	public void execute(Map<String, Object> data, String[] args) throws Exception {

		// call wait
		wait.execute(data, new String[] { ACWait.NAME, args[1], "visible" });

		// call text
		text.execute(data, new String[] { EMText.NAME, args[1], args[2] });
	}
}
