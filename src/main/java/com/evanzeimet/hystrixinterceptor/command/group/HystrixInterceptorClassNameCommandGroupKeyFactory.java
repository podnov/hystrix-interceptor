package com.evanzeimet.hystrixinterceptor.command.group;

import javax.interceptor.InvocationContext;

import com.evanzeimet.hystrixinterceptor.HystrixInterceptorUtils;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixInterceptorClassNameCommandGroupKeyFactory
		implements HystrixInterceptorCommandGroupKeyFactory {

	private static final HystrixInterceptorUtils utils = new HystrixInterceptorUtils();

	@Override
	public HystrixCommandGroupKey create(InvocationContext invocationContext) {
		String className = invocationContext.getMethod().getDeclaringClass().getSimpleName();
		return utils.createCommandGroupKey(className);
	}

}
