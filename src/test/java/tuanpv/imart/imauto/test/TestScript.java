package tuanpv.imart.imauto.test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class TestScript {
	public static void main(String[] args) throws ScriptException {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		String input = "21+31";
		System.out.println(engine.eval(input));
		
		
		engine.eval("alert ('Hello world!');");
	}
}
