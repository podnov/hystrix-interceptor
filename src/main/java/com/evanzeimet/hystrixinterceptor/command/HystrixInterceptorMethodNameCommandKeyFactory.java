package com.evanzeimet.hystrixinterceptor.command;

import javax.interceptor.InvocationContext;

import com.evanzeimet.hystrixinterceptor.HystrixInterceptorUtils;
import com.netflix.hystrix.HystrixCommandKey;

public class HystrixInterceptorMethodNameCommandKeyFactory
		implements HystrixInterceptorCommandKeyFactory {

	private static final HystrixInterceptorUtils utils = new HystrixInterceptorUtils();

	@Override
	public HystrixCommandKey create(InvocationContext invocationContext) {
		String methodName = invocationContext.getMethod().getName();
		return utils.createCommandKey(methodName);
	}

}
