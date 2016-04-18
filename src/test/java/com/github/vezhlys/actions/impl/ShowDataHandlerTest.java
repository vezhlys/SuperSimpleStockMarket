package com.github.vezhlys.actions.impl;

import org.junit.Assert;
import org.junit.Test;

public class ShowDataHandlerTest {

	@Test
	public void isShowDataActionHandlerSingleton() {
		ShowDataActionHandler handler1 = ShowDataActionHandler.getInstance();
		ShowDataActionHandler handler2 = ShowDataActionHandler.getInstance();
		Assert.assertEquals(handler1, handler2);
	}
}
