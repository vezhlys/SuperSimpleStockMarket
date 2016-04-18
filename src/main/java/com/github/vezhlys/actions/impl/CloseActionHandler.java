package com.github.vezhlys.actions.impl;

import com.github.vezhlys.actions.ActionHandler;

/**
 * Close command action handler. Closes the application and returns to console.
 */
public class CloseActionHandler implements ActionHandler {

	private CloseActionHandler() {
	}

	@Override
	public void action() {
		System.out.println("Exiting the application...");
		System.exit(0);
	}

	public static CloseActionHandler getInstance() {
		return CloseActionHandlerSingleton.INSTANCE;
	}

	private static class CloseActionHandlerSingleton {
		private static final CloseActionHandler INSTANCE = new CloseActionHandler();
	}
}
