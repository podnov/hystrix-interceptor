package com.evanzeimet.hystrixinterceptor.command.fallback;

import javax.interceptor.InvocationContext;

import com.evanzeimet.hystrixinterceptor.HystrixInterceptorUtils;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class UndefinedHystrixInterceptorCommandFallbackHandler
		implements HystrixInterceptorCommandFallbackHandler {

	private static final HystrixInterceptorUtils utils = new HystrixInterceptorUtils();
	private static final HystrixCommandGroupKey commandGroupKey = utils.createDefaultCommandGroupKey(UndefinedHystrixInterceptorCommandFallbackHandler.class);
	private static final UndefinedHystrixInterceptorFallbackHandlerCommand command = new UndefinedHystrixInterceptorFallbackHandlerCommand();

	@Override
	public Object getFallback(InvocationContext invocationContext) {
		return command.getFallback();
	}

	/**
	 * A subclass of {@link HystrixCommand} that exposes {@code getFalback}.
	 * This class is for internal purposes only.
	 */
	private static class UndefinedHystrixInterceptorFallbackHandlerCommand
			extends HystrixCommand<Object> {

		protected UndefinedHystrixInterceptorFallbackHandlerCommand() {
			super(commandGroupKey);
		}

		@Override
		public Object getFallback() {
			return super.getFallback();
		};

		@Override
		protected Object run() throws Exception {
			throw new UnsupportedOperationException("This command is not intended to be run.");
		}

	}
}
