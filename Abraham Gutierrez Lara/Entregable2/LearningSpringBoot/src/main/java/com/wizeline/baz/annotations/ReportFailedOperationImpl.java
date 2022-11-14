package com.wizeline.baz.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.wizeline.baz.utils.BuildOperationData;
import com.wizeline.baz.utils.RegisterOperationThread;

@Aspect
@Component
public class ReportFailedOperationImpl {
	
	@Around("execution(* com.wizeline.baz..*(..)) && @annotation(annotationTx)")
	public Object aroundTransaccion(ProceedingJoinPoint joinPoint, ReportFailedOperation annotationTx) throws Throwable {
		Object result = null;
		
		try {
			result = joinPoint.proceed();
		} catch (Exception e) {
			if(!annotationTx.exception().isInstance(e)) {
				throw e;
			}
			BuildOperationData buildOperationData = (BuildOperationData) e;
			RegisterOperationThread registerOperationThread = new RegisterOperationThread(
													buildOperationData.operationData(), annotationTx.topic());
			registerOperationThread.start();
			throw e;
		}
		
		return result;
	}
}
