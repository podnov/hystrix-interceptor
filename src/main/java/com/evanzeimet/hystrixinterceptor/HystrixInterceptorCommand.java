package com.evanzeimet.hystrixinterceptor;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

public class HystrixInterceptorCommand extends HystrixCommand<Object> {

	private static final String INTERCEPT_ANNOTATION_NAME = HystrixIntercept.class.getSimpleName();

	private final InvocationContext invocationContext;

	protected HystrixInterceptorCommand(InvocationContext invocationContext) {
		super(createSetter(invocationContext));
		this.invocationContext = invocationContext;
	}

	protected static HystrixCommandKey createCommandKey(Method method,
			HystrixIntercept annotation) {
		String rawCommandKey = annotation.commandKey();

		if ("".equals(rawCommandKey)) {
			rawCommandKey = method.getName();
		}

		return createCommandKey(rawCommandKey);
	}

	protected static HystrixCommandKey createCommandKey(String rawCommandKey) {
		return HystrixCommandKey.Factory.asKey(rawCommandKey);
	}

	protected static String createFullyQuailifiedMethodName(Method method) {
		String className = method.getClass().getCanonicalName();
		String methodName = method.getName();
		return String.format("%s::%s", className, methodName);
	}

	protected static HystrixCommandGroupKey createGroupKey(HystrixIntercept annotation) {
		String rawGroupKey = annotation.groupKey();
		return createGroupKey(rawGroupKey);
	}

	protected static HystrixCommandGroupKey createGroupKey(String rawGroupKey) {
		return HystrixCommandGroupKey.Factory.asKey(rawGroupKey);
	}

	protected static Setter createSetter(InvocationContext invocationContext) {
		Method method = invocationContext.getMethod();
		HystrixIntercept annotation = getHystrixInterceptAnnotation(method);

		HystrixCommandGroupKey groupKey = createGroupKey(annotation);
		HystrixCommandKey commandKey = createCommandKey(method, annotation);

		return Setter.withGroupKey(groupKey)
				.andCommandKey(commandKey);
	}

	protected static HystrixIntercept getHystrixInterceptAnnotation(Method method) {
		HystrixIntercept result = method.getAnnotation(HystrixIntercept.class);

		if (result == null) {
			Class<?> declaringClass = method.getDeclaringClass();
			result = declaringClass.getAnnotation(HystrixIntercept.class);
		}

		if (result == null) {
			String methodName = createFullyQuailifiedMethodName(method);
			String message = String.format("Could not find %s annotation on method [%s]",
					INTERCEPT_ANNOTATION_NAME,
					methodName);
			throw new RuntimeException(message);
		}

		return result;
	}

	@Override
	protected Object run() throws Exception {
		return invocationContext.proceed();
	}

}
