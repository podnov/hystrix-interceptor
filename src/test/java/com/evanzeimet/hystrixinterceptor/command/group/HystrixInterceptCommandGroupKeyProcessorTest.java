package com.evanzeimet.hystrixinterceptor.command.group;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import org.junit.Before;
import org.junit.Test;

import com.evanzeimet.hystrixinterceptor.HystrixIntercept;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HystrixInterceptCommandGroupKeyProcessorTest {

	protected static final String STATIC_KEY = "StaticCommandGroupKey";

	private HystrixInterceptCommandGroupKeyProcessor processor;

	@Before
	public void setUp() {
		processor = new HystrixInterceptCommandGroupKeyProcessor();
	}

	@Test
	public void process_annotation_notPopulated() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethod_annotation_notPopulated");

		InvocationContext givenInvocationContext = mock(InvocationContext.class);
		doReturn(givenMethod).when(givenInvocationContext).getMethod();

		HystrixCommandGroupKey actuaCommandGroupKey = processor.process(givenInvocationContext);

		String actualRawCommandGroupKey = actuaCommandGroupKey.name();
		String expectedRawCommandGroupKey = TestServiceClass.class.getSimpleName();

		assertEquals(expectedRawCommandGroupKey, actualRawCommandGroupKey);
	}

	@Test
	public void process_annotation_staticKey() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethod_annotation_staticKey");

		InvocationContext givenInvocationContext = mock(InvocationContext.class);
		doReturn(givenMethod).when(givenInvocationContext).getMethod();

		HystrixCommandGroupKey actuaCommandGroupKey = processor.process(givenInvocationContext);

		String actualRawCommandGroupKey = actuaCommandGroupKey.name();
		String expectedRawCommandGroupKey = STATIC_KEY;

		assertEquals(expectedRawCommandGroupKey, actualRawCommandGroupKey);
	}

	@Test
	public void process_noAnnotation() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethod_noAnnotation");

		InvocationContext givenInvocationContext = mock(InvocationContext.class);
		doReturn(givenMethod).when(givenInvocationContext).getMethod();

		HystrixCommandGroupKey actuaCommandGroupKey = processor.process(givenInvocationContext);

		String actualRawCommandGroupKey = actuaCommandGroupKey.name();
		String expectedRawCommandGroupKey = TestServiceClass.class.getSimpleName();

		assertEquals(expectedRawCommandGroupKey, actualRawCommandGroupKey);
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

		@HystrixIntercept(commandGroupKey = STATIC_KEY)
		public void testServiceMethod_annotation_staticKey() {

		}

		public void testServiceMethod_noAnnotation() {

		}

	}

}
