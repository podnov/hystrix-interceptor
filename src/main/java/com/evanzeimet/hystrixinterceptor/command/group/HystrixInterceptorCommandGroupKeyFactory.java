package com.evanzeimet.hystrixinterceptor.command.group;

import javax.interceptor.InvocationContext;

import com.netflix.hystrix.HystrixCommandGroupKey;

public interface HystrixInterceptorCommandGroupKeyFactory {

	HystrixCommandGroupKey create(InvocationContext invocationContext);

}
