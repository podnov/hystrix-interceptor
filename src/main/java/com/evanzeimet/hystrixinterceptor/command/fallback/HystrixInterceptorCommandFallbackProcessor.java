package com.evanzeimet.hystrixinterceptor.command.fallback;

import javax.interceptor.InvocationContext;

import com.evanzeimet.hystrixinterceptor.HystrixIntercept;
import com.evanzeimet.hystrixinterceptor.HystrixInterceptorUtils;

public class HystrixInterceptorCommandFallbackProcessor {

	private static final HystrixInterceptorUtils utils = new HystrixInterceptorUtils();

	public Object process(InvocationContext invocationContext) {
		HystrixInterceptorCommandFallbackHandler handler = createHandler(invocationContext);

		return handler.getFallback(invocationContext);
	}

	protected HystrixInterceptorCommandFallbackHandler createHandler(InvocationContext invocationContext) {
		HystrixIntercept annotation = utils.getHystrixInterceptAnnotation(invocationContext);
		Class<? extends HystrixInterceptorCommandFallbackHandler> factoryClass = annotation.fallbackHandler();

		return utils.createDefaultInstance(factoryClass);
	}

}
