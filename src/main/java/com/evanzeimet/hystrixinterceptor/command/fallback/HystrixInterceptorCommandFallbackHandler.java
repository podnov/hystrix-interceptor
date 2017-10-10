package com.evanzeimet.hystrixinterceptor.command.fallback;

import javax.interceptor.InvocationContext;

public interface HystrixInterceptorCommandFallbackHandler {

	Object getFallback(InvocationContext invocationContext);

}
