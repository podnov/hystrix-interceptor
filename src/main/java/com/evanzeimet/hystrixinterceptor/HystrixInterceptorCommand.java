package com.evanzeimet.hystrixinterceptor;

import javax.interceptor.InvocationContext;

import com.evanzeimet.hystrixinterceptor.command.HystrixInterceptCommandKeyProcessor;
import com.evanzeimet.hystrixinterceptor.command.group.HystrixInterceptCommandGroupKeyProcessor;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

public class HystrixInterceptorCommand extends HystrixCommand<Object> {

	private static final HystrixInterceptCommandKeyProcessor commandKeyProcessor = new HystrixInterceptCommandKeyProcessor();
	private static final HystrixInterceptCommandGroupKeyProcessor commandGroupKeyProcessor = new HystrixInterceptCommandGroupKeyProcessor();

	private final InvocationContext invocationContext;

	protected HystrixInterceptorCommand(InvocationContext invocationContext) {
		super(createSetter(invocationContext));
		this.invocationContext = invocationContext;
	}

	protected static Setter createSetter(InvocationContext invocationContext) {
		HystrixCommandGroupKey groupKey = commandGroupKeyProcessor.process(invocationContext);
		HystrixCommandKey commandKey = commandKeyProcessor.process(invocationContext);

		return Setter.withGroupKey(groupKey)
				.andCommandKey(commandKey);
	}

	@Override
	protected Object run() throws Exception {
		return invocationContext.proceed();
	}

}
