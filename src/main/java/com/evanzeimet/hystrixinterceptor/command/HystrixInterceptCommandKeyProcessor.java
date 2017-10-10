package com.evanzeimet.hystrixinterceptor.command;

import javax.interceptor.InvocationContext;

import com.evanzeimet.hystrixinterceptor.HystrixIntercept;
import com.evanzeimet.hystrixinterceptor.HystrixInterceptorUtils;
import com.netflix.hystrix.HystrixCommandKey;

public class HystrixInterceptCommandKeyProcessor {

	private static final HystrixInterceptorUtils utils = new HystrixInterceptorUtils();

	protected HystrixCommandKey invokeFactory(HystrixIntercept annotation,
			InvocationContext invocationContext) {
		Class<? extends HystrixInterceptorCommandKeyFactory> factoryClass = annotation.commandKeyFactory();
		HystrixInterceptorCommandKeyFactory factory = utils.createDefaultInstance(factoryClass);

		return factory.create(invocationContext);
	}

	public HystrixCommandKey process(InvocationContext invocationContext) {
		HystrixCommandKey result;
		HystrixIntercept annotation = utils.getHystrixInterceptAnnotation(invocationContext);

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
