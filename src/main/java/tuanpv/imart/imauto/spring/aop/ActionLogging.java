package tuanpv.imart.imauto.spring.aop;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
public class ActionLogging {

	@Before("execution(* tuanpv.imart.imauto.spring.Action.execute(..))")
	private void beforeExecute(JoinPoint joinPoint) {
		Object object = joinPoint.getArgs()[1];
		if (object != null && object instanceof String[]) {
			String[] array = (String[]) object;
			System.out.printf("> %-14s | %-100s | %-40s \n"
					, array[0]
					, array.length>1? array[1] : StringUtils.EMPTY
					, array.length>2? array[2] : StringUtils.EMPTY);
		}
	}
}
