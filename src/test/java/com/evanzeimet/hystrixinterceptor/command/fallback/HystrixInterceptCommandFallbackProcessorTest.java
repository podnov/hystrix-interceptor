package com.evanzeimet.hystrixinterceptor.command.fallback;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import org.junit.Before;
import org.junit.Test;

import com.evanzeimet.hystrixinterceptor.HystrixIntercept;

public class HystrixInterceptCommandFallbackProcessorTest {

	private HystrixInterceptorCommandFallbackProcessor processor;

	@Before
	public void setUp() {
		processor = new HystrixInterceptorCommandFallbackProcessor();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void process_annotation_notPopulated() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethod_annotation_notPopulated");

		InvocationContext givenInvocationContext = mock(InvocationContext.class);
		doReturn(givenMethod).when(givenInvocationContext).getMethod();

		processor.process(givenInvocationContext);
	}

	@Test
	public void process_annotation_populated() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethod_annotation_populated");

		InvocationContext givenInvocationContext = mock(InvocationContext.class);
		doReturn(givenMethod).when(givenInvocationContext).getMethod();

		Object actual = processor.process(givenInvocationContext);

		assertEquals(TestServiceFallbackHandler.FALLBACK_RESULT, actual);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void process_noAnnotation() throws NoSuchMethodException {
		Method givenMethod = getMethod("testServiceMethod_noAnnotation");

		InvocationContext givenInvocationContext = mock(InvocationContext.class);
		doReturn(givenMethod).when(givenInvocationContext).getMethod();

		processor.process(givenInvocationContext);
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

		@HystrixIntercept(fallbackHandler = TestServiceFallbackHandler.class)
		public void testServiceMethod_annotation_populated() {

		}

		public void testServiceMethod_noAnnotation() {

		}

	}

	protected static class TestServiceFallbackHandler
			implements HystrixInterceptorCommandFallbackHandler {

		private static Object FALLBACK_RESULT = new Object();

		public TestServiceFallbackHandler() {

		}

		@Override
		public Object getFallback(InvocationContext invocationContext) {
			return FALLBACK_RESULT;
		}

	}
}
