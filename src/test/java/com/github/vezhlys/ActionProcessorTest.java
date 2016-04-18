package com.github.vezhlys;

import org.junit.Assert;
import org.junit.Test;

import com.github.vezhlys.exceptions.NoConsoleFoundException;

public class ActionProcessorTest {

	@Test
	public void isActionArgumentProcessorSingleton() {
		ActionProcessor processor1 = ActionProcessor.getInstance();
		ActionProcessor processor2 = ActionProcessor.getInstance();
		Assert.assertEquals(processor1, processor2);
	}

	@Test(expected = NoConsoleFoundException.class)
	public void throwEmptyConsoleExceptionIfConsoleIsNull() throws NoConsoleFoundException {
		ActionProcessor.getInstance().process(null);
	}

}
