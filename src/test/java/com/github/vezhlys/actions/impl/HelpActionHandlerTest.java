package com.github.vezhlys.actions.impl;

import org.junit.Assert;
import org.junit.Test;

public class HelpActionHandlerTest {

	@Test
	public void isHelpActionHandlerSingleton() {
		HelpActionHandler handler1 = HelpActionHandler.getInstance();
		HelpActionHandler handler2 = HelpActionHandler.getInstance();
		Assert.assertEquals(handler1, handler2);
	}
}
