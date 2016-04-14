package com.evanzeimet.hystrixinterceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class HystrixInterceptorUtilsTest {

	protected static final String CLASS_GROUP = "ClassGroup";
	protected static final String METHOD_GROUP = "MethodGroup";
	protected static final String PRE_DEFINED_COMMAND_KEY = "PreDefinedCommandKey";

	private HystrixInterceptorUtils utils;

	@Before
	public void setUp() {
		utils = new HystrixInterceptorUtils();
	}

	@Test
	public void getHystrixInterceptAnnotation_onMethod() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethodWithCommandAnnotation");

		HystrixIntercept actualAnnotation = utils.getHystrixInterceptAnnotation(givenMethod);

		String actualGroupKey = actualAnnotation.commandGroupKey();
		String expectedGroupKey = METHOD_GROUP;

		assertEquals(expectedGroupKey, actualGroupKey);
	}

	@Test
	public void getHystrixInterceptAnnotation_onClass() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethodWithoutCommandAnnotation");

		HystrixIntercept actualAnnotation = utils.getHystrixInterceptAnnotation(givenMethod);

		String actualGroupKey = actualAnnotation.commandGroupKey();
		String expectedGroupKey = CLASS_GROUP;

		assertEquals(expectedGroupKey, actualGroupKey);
	}

	@Test
	public void isBlank_null() {
		String givenValue = null;

		boolean actual = utils.isBlank(givenValue);

		assertTrue(actual);
	}

	@Test
	public void isBlank_noCharacters() {
		String givenValue = "";

		boolean actual = utils.isBlank(givenValue);

		assertTrue(actual);
	}

	@Test
	public void isBlank_notBlank() {
		String givenValue = "ohai";

		boolean actual = utils.isBlank(givenValue);

		assertFalse(actual);
	}

	@Test
	public void isBlank_whitespace() {
		String givenValue = "  ";

		boolean actual = utils.isBlank(givenValue);

		assertTrue(actual);
	}

	protected Method getMethod(String methodName) throws NoSuchMethodException {
		return TestServiceClass.class.getMethod(methodName);
	}

	@SuppressWarnings("unused")
	@HystrixIntercept(commandGroupKey = CLASS_GROUP)
	private static class TestServiceClass {

		@HystrixIntercept(commandGroupKey = METHOD_GROUP)
		public void testServiceMethodWithCommandAnnotation() {

		}

		public void testServiceMethodWithoutCommandAnnotation() {

		}

		@HystrixIntercept(commandGroupKey = METHOD_GROUP,
				commandKey = "")
		public void testServiceMethodWithEmptyStringCommandKey() {

		}

		@HystrixIntercept(commandGroupKey = METHOD_GROUP,
				commandKey = PRE_DEFINED_COMMAND_KEY)
		public void testServiceMethodWithPopulatedCommandKey() {

		}

	}

}
