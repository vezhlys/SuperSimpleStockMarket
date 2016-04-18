package com.github.vezhlys;

import java.io.Console;

import org.apache.commons.lang3.StringUtils;

import com.github.vezhlys.actions.ActionHandler;
import com.github.vezhlys.actions.impl.CloseActionHandler;
import com.github.vezhlys.actions.impl.DefaultActionHandler;
import com.github.vezhlys.actions.impl.HelpActionHandler;
import com.github.vezhlys.actions.impl.RecordTradeActionHandler;
import com.github.vezhlys.actions.impl.ShowDataActionHandler;
import com.github.vezhlys.exceptions.InvalidTradeArgumentsException;
import com.github.vezhlys.exceptions.NoConsoleFoundException;
import com.github.vezhlys.model.enums.ActionType;

/**
 * Command line action processor.
 * 
 * @see ActionType
 *
 */
public class ActionProcessor {

	private ActionProcessor() {
	}

	public static ActionProcessor getInstance() {
		return ActionArgumentProcessorSingleton.INSTANCE;
	}

	public void process(final Console console) throws NoConsoleFoundException {
		if (console == null) {
			throw new NoConsoleFoundException();
		}
		while (true) {
			String input = StringUtils.trim(readLineFromConsole(console));
			try {
				getActionHandler(ActionType.getAction(getActionString(input)), input).action();
			} catch (InvalidTradeArgumentsException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private ActionHandler getActionHandler(final ActionType action, final String input)
			throws InvalidTradeArgumentsException {
		switch (action) {
		case HELP:
			return HelpActionHandler.getInstance();
		case RECORD_TRADE:
			return new RecordTradeActionHandler(input);
		case SHOW_DATA:
			return ShowDataActionHandler.getInstance();
		case CLOSE_APP:
			return CloseActionHandler.getInstance();
		case UNKOWN:
		default:
			return DefaultActionHandler.getInstance();
		}
	}

	private String getActionString(final String input) {
		return StringUtils.defaultString(input).split("\\s")[0];
	}

	private String readLineFromConsole(final Console console) {
		return console.readLine("Please enter the action, type \"help\" or \"h\" for help\n");
	}

	private static class ActionArgumentProcessorSingleton {
		private static final ActionProcessor INSTANCE = new ActionProcessor();
	}

}
