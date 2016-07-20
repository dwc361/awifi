package org.roof.log;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * aop日志记录 将所有 org.seek 包下, 以Service结尾的类产生异常记录
 * 
 * Action中记录日志
 * 
 * @see ExceptionIntercept
 * @author liuxin 2011-3-24
 * 
 */
@Aspect
@Component
public class RoofLogAop {

	@Around("execution(* *..*Service.*(..))")
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = null;
		try {
			retVal = pjp.proceed();
			pjp.getTarget();
		} catch (Exception e) {
			Logger logger = Logger.getLogger(pjp.getTarget().getClass());
			logger.error(e);
			e.printStackTrace();
			throw e;
		}
		return retVal;
	}
}
