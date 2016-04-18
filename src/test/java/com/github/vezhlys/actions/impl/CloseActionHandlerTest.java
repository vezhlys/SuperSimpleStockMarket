package com.github.vezhlys.actions.impl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class CloseActionHandlerTest {
	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@Test
	public void isCloseActionHandlerSingleton() {
		CloseActionHandler handler1 = CloseActionHandler.getInstance();
		CloseActionHandler handler2 = CloseActionHandler.getInstance();
		Assert.assertEquals(handler1, handler2);
	}

	@Test
	public void expectSystemExitWithStatusCode0() {
		exit.expectSystemExitWithStatus(0);
		CloseActionHandler.getInstance().action();
	}
}
