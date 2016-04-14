package com.evanzeimet.hystrixinterceptor;

import java.lang.reflect.Method;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

public class HystrixInterceptorUtils {

	private static final String INTERCEPT_ANNOTATION_NAME = HystrixIntercept.class.getSimpleName();

	public HystrixInterceptorUtils() {

	}

	public HystrixCommandKey createCommandKey(String rawCommandKey) {
		return HystrixCommandKey.Factory.asKey(rawCommandKey);
	}

	public String createFullyQuailifiedMethodName(Method method) {
		String className = method.getClass().getCanonicalName();
		String methodName = method.getName();
		return String.format("%s::%s", className, methodName);
	}

	public HystrixCommandGroupKey createCommandGroupKey(String rawGroupKey) {
		return HystrixCommandGroupKey.Factory.asKey(rawGroupKey);
	}

	public HystrixIntercept getHystrixInterceptAnnotation(Method method) {
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

	public boolean isBlank(String value) {
		boolean result = (value == null);
		result = (result || "".equals(value.trim()));
		return result;
	}

	public boolean isNotBlank(String value) {
		return !isBlank(value);
	}

}
