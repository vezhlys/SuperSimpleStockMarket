package com.github.vezhlys.model.enums;

import java.util.Arrays;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

/**
 * Actions available for user in console.
 */
public enum ActionType {
	RECORD_TRADE("trade", "t"), CLOSE_APP("quit", "q"), SHOW_DATA("show", "s"), HELP("help", "h"), UNKOWN("", "");

	private String argument;
	private String shortArgument;

	private ActionType(String argument, String shortArgument) {
		this.argument = argument;
		this.shortArgument = shortArgument;
	}

	public static ActionType getAction(final String argument) {
		final String trimmedArg = StringUtils.trim(argument);
		return Arrays.stream(ActionType.values())
				.filter(isKnownAction(trimmedArg))
				.findFirst()
				.orElse(ActionType.UNKOWN);
	}

	private static Predicate<ActionType> isKnownAction(final String argument) {
		return type -> type.argument.equals(argument) || type.shortArgument.equals(argument);
	}
}
