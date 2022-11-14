package com.wizeline.baz.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.wizeline.baz.utils.BuildOperationData;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ReportFailedOperation {
	
	String topic();
	Class<? extends BuildOperationData> exception();
}
