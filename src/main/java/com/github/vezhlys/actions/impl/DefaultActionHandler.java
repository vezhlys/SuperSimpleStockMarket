package com.github.vezhlys.actions.impl;

import com.github.vezhlys.actions.ActionHandler;

/**
 * Unknown command action handler. Shows unknown action text.
 */
public class DefaultActionHandler implements ActionHandler {
	private static final String UKNWOWN_ACTION = "Unknown action";

	@Override
	public void action() {
		System.out.println(UKNWOWN_ACTION);
	}

	public static DefaultActionHandler getInstance() {
		return DefaultActionHandlerSingleton.INSTANCE;
	}

	private static class DefaultActionHandlerSingleton {
		private static final DefaultActionHandler INSTANCE = new DefaultActionHandler();
	}

}
