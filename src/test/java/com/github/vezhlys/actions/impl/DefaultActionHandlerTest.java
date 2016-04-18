package com.github.vezhlys.actions.impl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class DefaultActionHandlerTest {
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	DefaultActionHandler handler1 = DefaultActionHandler.getInstance();

	@Test
	public void isDefaultActionHandlerSingleton() {
		DefaultActionHandler handler2 = DefaultActionHandler.getInstance();
		Assert.assertEquals(handler1, handler2);
	}

	@Test
	public void actionOutputsUnknownActionMessage() {
		handler1.action();
		Assert.assertEquals("Unknown action\n", systemOutRule.getLog());
	}

}
