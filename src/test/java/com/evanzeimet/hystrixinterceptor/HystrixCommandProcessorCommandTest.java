package com.evanzeimet.hystrixinterceptor;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import org.junit.Test;

import com.netflix.hystrix.HystrixCommandKey;

public class HystrixCommandProcessorCommandTest {

	protected static final String CLASS_GROUP = "ClassGroup";
	protected static final String METHOD_GROUP = "MethodGroup";
	protected static final String PRE_DEFINED_COMMAND_KEY = "PreDefinedCommandKey";

	@Test
	public void createCommandKey_emptyString() throws NoSuchMethodException {
		String givenMethodName = "testServiceMethodWithEmptyStringCommandKey";

		Method givenMethod = getMethod(givenMethodName);
		HystrixIntercept annotation = HystrixInterceptorCommand.getHystrixInterceptAnnotation(givenMethod);

		HystrixCommandKey actuaCommandKey = HystrixInterceptorCommand.createCommandKey(givenMethod,
				annotation);

		String actualRawCommandKey = actuaCommandKey.name();
		String expectedRawCommandKey = givenMethodName;

		assertEquals(expectedRawCommandKey, actualRawCommandKey);
	}

	@Test
	public void createCommandKey_populated() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethodWithPopulatedCommandKey");
		HystrixIntercept annotation = HystrixInterceptorCommand.getHystrixInterceptAnnotation(givenMethod);

		HystrixCommandKey actuaCommandKey = HystrixInterceptorCommand.createCommandKey(givenMethod,
				annotation);

		String actualRawCommandKey = actuaCommandKey.name();
		String expectedRawCommandKey = PRE_DEFINED_COMMAND_KEY;

		assertEquals(expectedRawCommandKey, actualRawCommandKey);
	}

	@Test
	public void getMethodCommandAnnotation_onMethod() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethodWithCommandAnnotation");

		HystrixIntercept actualAnnotation = HystrixInterceptorCommand.getHystrixInterceptAnnotation(givenMethod);

		String actualGroupKey = actualAnnotation.groupKey();
		String expectedGroupKey = METHOD_GROUP;

		assertEquals(expectedGroupKey, actualGroupKey);
	}

	@Test
	public void getMethodCommandAnnotation_onClass() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethodWithoutCommandAnnotation");

		HystrixIntercept actualAnnotation = HystrixInterceptorCommand.getHystrixInterceptAnnotation(givenMethod);

		String actualGroupKey = actualAnnotation.groupKey();
		String expectedGroupKey = CLASS_GROUP;

		assertEquals(expectedGroupKey, actualGroupKey);
	}

	protected Method getMethod(String methodName) throws NoSuchMethodException {
		return TestServiceClass.class.getMethod(methodName);
	}

	@SuppressWarnings("unused")
	@HystrixIntercept(groupKey = CLASS_GROUP)
	private static class TestServiceClass {


		@HystrixIntercept(groupKey = METHOD_GROUP)
		public void testServiceMethodWithCommandAnnotation() {

		}

		public void testServiceMethodWithoutCommandAnnotation() {

		}

		@HystrixIntercept(groupKey = METHOD_GROUP,
				commandKey = "")
		public void testServiceMethodWithEmptyStringCommandKey() {

		}

		@HystrixIntercept(groupKey = METHOD_GROUP,
				commandKey = PRE_DEFINED_COMMAND_KEY)
		public void testServiceMethodWithPopulatedCommandKey() {

		}

	}

}
