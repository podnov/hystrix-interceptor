package com.evanzeimet.hystrixinterceptor.command.group;

import javax.interceptor.InvocationContext;

import com.evanzeimet.hystrixinterceptor.HystrixIntercept;
import com.evanzeimet.hystrixinterceptor.HystrixInterceptorUtils;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixInterceptCommandGroupKeyProcessor {

	private static final HystrixInterceptorUtils utils = new HystrixInterceptorUtils();

	protected HystrixCommandGroupKey invokeFactory(HystrixIntercept annotation,
			InvocationContext invocationContext) {
		Class<? extends HystrixInterceptorCommandGroupKeyFactory> factoryClass = annotation.commandGroupKeyFactory();
		HystrixInterceptorCommandGroupKeyFactory factory = utils.createDefaultInstance(factoryClass);

		return factory.create(invocationContext);
	}

	public HystrixCommandGroupKey process(InvocationContext invocationContext) {
		HystrixCommandGroupKey result;

		HystrixIntercept annotation = utils.getHystrixInterceptAnnotation(invocationContext);

		String rawCommandGroupKey = annotation.commandGroupKey();
		boolean staticKeyIsSet = utils.isNotBlank(rawCommandGroupKey);


		if (staticKeyIsSet) {
			result = utils.createCommandGroupKey(rawCommandGroupKey);
		} else {
			result = invokeFactory(annotation, invocationContext);
		}

		return result;
	}

}
