package com.evanzeimet.hystrixinterceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@HystrixIntercept
public class HystrixInterceptor {

	@AroundInvoke
	public Object intercept(InvocationContext invocationContext) {
		return new HystrixInterceptorCommand(invocationContext).execute();
	}

}
