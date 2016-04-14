package com.evanzeimet.hystrixinterceptor.command;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import com.evanzeimet.hystrixinterceptor.HystrixIntercept;
import com.evanzeimet.hystrixinterceptor.HystrixInterceptorUtils;
import com.netflix.hystrix.HystrixCommandKey;

public class HystrixInterceptCommandKeyProcessor {

	private static final HystrixInterceptorUtils utils = new HystrixInterceptorUtils();

	protected HystrixCommandKey invokeFactory(HystrixIntercept annotation,
			InvocationContext invocationContext) {
		Class<? extends HystrixInterceptorCommandKeyFactory> factoryClass = annotation.commandKeyFactory();
		HystrixInterceptorCommandKeyFactory factory;

		try {
			factory = factoryClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			String message = String.format("Could not invoke newInstance on [%s]", annotation);
			throw new RuntimeException(message, e);
		}

		return factory.create(invocationContext);
	}

	public HystrixCommandKey process(InvocationContext invocationContext) {
		HystrixCommandKey result = null;
		Method method = invocationContext.getMethod();
		HystrixIntercept annotation = utils.getHystrixInterceptAnnotation(method);

		String rawCommandKey = annotation.commandKey();
		boolean staticKeyIsSet = utils.isNotBlank(rawCommandKey);

		if (staticKeyIsSet) {
			result = utils.createCommandKey(rawCommandKey);
		} else {
			result = invokeFactory(annotation, invocationContext);
		}

		return result;
	}

}
