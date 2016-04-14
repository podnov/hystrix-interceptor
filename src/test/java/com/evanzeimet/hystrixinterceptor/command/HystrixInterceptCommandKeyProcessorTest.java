package com.evanzeimet.hystrixinterceptor.command;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import org.junit.Before;
import org.junit.Test;

import com.evanzeimet.hystrixinterceptor.HystrixIntercept;
import com.netflix.hystrix.HystrixCommandKey;

public class HystrixInterceptCommandKeyProcessorTest {

	protected static final String STATIC_KEY = "StaticCommandKey";

	private HystrixInterceptCommandKeyProcessor processor;

	@Before
	public void setUp() {
		processor = new HystrixInterceptCommandKeyProcessor();
	}

	@Test
	public void process_annotation_notPopulated() throws NoSuchMethodException {
		String givenMethodName = "testServiceMethod_annotation_notPopulated";
		Method givenMethod = getMethod(givenMethodName);

		InvocationContext givenInvocationContext = mock(InvocationContext.class);
		doReturn(givenMethod).when(givenInvocationContext).getMethod();

		HystrixCommandKey actuaCommandKey = processor.process(givenInvocationContext);

		String actualRawCommandKey = actuaCommandKey.name();
		String expectedRawCommandKey = givenMethodName;

		assertEquals(expectedRawCommandKey, actualRawCommandKey);
	}

	@Test
	public void process_annotation_staticKey() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethod_annotation_staticKey");

		InvocationContext givenInvocationContext = mock(InvocationContext.class);
		doReturn(givenMethod).when(givenInvocationContext).getMethod();

		HystrixCommandKey actuaCommandKey = processor.process(givenInvocationContext);

		String actualRawCommandKey = actuaCommandKey.name();
		String expectedRawCommandKey = STATIC_KEY;

		assertEquals(expectedRawCommandKey, actualRawCommandKey);
	}

	@Test
	public void process_noAnnotation() throws NoSuchMethodException {
		String givenMethodName = "testServiceMethod_noAnnotation";
		Method givenMethod = getMethod(givenMethodName);

		InvocationContext givenInvocationContext = mock(InvocationContext.class);
		doReturn(givenMethod).when(givenInvocationContext).getMethod();

		HystrixCommandKey actuaCommandKey = processor.process(givenInvocationContext);

		String actualRawCommandKey = actuaCommandKey.name();
		String expectedRawCommandKey = givenMethodName;

		assertEquals(expectedRawCommandKey, actualRawCommandKey);
	}

	protected Method getMethod(String methodName) throws NoSuchMethodException {
		return TestServiceClass.class.getMethod(methodName);
	}

	@SuppressWarnings("unused")
	@HystrixIntercept
	private static class TestServiceClass {

		@HystrixIntercept
		public void testServiceMethod_annotation_notPopulated() {

		}

		@HystrixIntercept(commandKey = STATIC_KEY)
		public void testServiceMethod_annotation_staticKey() {

		}

		public void testServiceMethod_noAnnotation() {

		}
	}

}
