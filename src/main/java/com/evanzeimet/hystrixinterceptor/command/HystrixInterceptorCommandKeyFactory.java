package com.evanzeimet.hystrixinterceptor.command;

import javax.interceptor.InvocationContext;

import com.netflix.hystrix.HystrixCommandKey;

public interface HystrixInterceptorCommandKeyFactory {

	HystrixCommandKey create(InvocationContext invocationContext);

}
