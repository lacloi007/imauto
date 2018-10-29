package tuanpv.imart.imauto.spring.aop;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import tuanpv.imart.imauto.spring.action.ACLog;

@Component
@Aspect
@Order(1)
public class ActionLogging {

	@Before("execution(* tuanpv.imart.imauto.spring.Action.execute(..))")
	private void beforeExecute(JoinPoint joinPoint) {
		Object object = joinPoint.getArgs()[1];
		Object target = joinPoint.getTarget();
		if (object != null && object instanceof String[]) {
			String[] array = (String[]) object;

			// String template = "%-14s | %-100s | %-40s";
			String param1 = array.length > 1 ? array[1] : StringUtils.EMPTY;
			String param2 = array.length > 2 ? array[2] : StringUtils.EMPTY;
			String template = "=> %s\n";

			// bypass display in case name is "log"
			if (!(target instanceof ACLog)) {
				if (!param1.equals(StringUtils.EMPTY)) {
					template += " + args[0] : %s\n";

					if (!param2.equals(StringUtils.EMPTY)) {
						template += " + args[1] : %s\n";
					}
				}
			}

			template += "\n";

			// log
			System.out.printf(template, array[0], param1, param2);
		}
	}
}