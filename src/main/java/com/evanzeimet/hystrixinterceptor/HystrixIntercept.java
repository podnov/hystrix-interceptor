package com.evanzeimet.hystrixinterceptor;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

import com.evanzeimet.hystrixinterceptor.command.HystrixInterceptorCommandKeyFactory;
import com.evanzeimet.hystrixinterceptor.command.HystrixInterceptorMethodNameCommandKeyFactory;
import com.evanzeimet.hystrixinterceptor.command.fallback.HystrixInterceptorCommandFallbackHandler;
import com.evanzeimet.hystrixinterceptor.command.fallback.UndefinedHystrixInterceptorCommandFallbackHandler;
import com.evanzeimet.hystrixinterceptor.command.group.HystrixInterceptorClassNameCommandGroupKeyFactory;
import com.evanzeimet.hystrixinterceptor.command.group.HystrixInterceptorCommandGroupKeyFactory;

@InterceptorBinding
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
public @interface HystrixIntercept {

	@Nonbinding
	String commandKey() default "";

	@Nonbinding
	String commandGroupKey() default "";

	@Nonbinding
	Class<? extends HystrixInterceptorCommandKeyFactory> commandKeyFactory() default HystrixInterceptorMethodNameCommandKeyFactory.class;

	@Nonbinding
	Class<? extends HystrixInterceptorCommandGroupKeyFactory> commandGroupKeyFactory() default HystrixInterceptorClassNameCommandGroupKeyFactory.class;

	@Nonbinding
	Class<? extends HystrixInterceptorCommandFallbackHandler> fallbackHandler() default UndefinedHystrixInterceptorCommandFallbackHandler.class;

}
