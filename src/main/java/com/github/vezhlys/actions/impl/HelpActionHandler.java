package com.github.vezhlys.actions.impl;

import com.github.vezhlys.actions.ActionHandler;

/**
 * Help command action handler. Shows help text.
 */
public class HelpActionHandler implements ActionHandler {

	private static final String HELP_TEXT = "help or h - show this help.\n"
			+ "trade or t - record a trade. format: trade <stock symbol> <buy|sell> <quantity> <price>. example: trade TEA buy 3 2.50\n"
			+ "show or s - show stock market status (All Share index, all stock tickers with current prices).\n"
			+ "quit or q - quit application.\n";

	private HelpActionHandler() {
	}

	@Override
	public void action() {
		System.out.println(HELP_TEXT);
	}

	public static HelpActionHandler getInstance() {
		return HelpActionHandlerSingleton.INSTANCE;
	}

	private static class HelpActionHandlerSingleton {
		private static final HelpActionHandler INSTANCE = new HelpActionHandler();
	}

}
