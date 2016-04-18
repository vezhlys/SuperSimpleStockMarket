package com.github.vezhlys.exceptions;

/**
 * Invalid trade arguments exception.
 */
public class InvalidTradeArgumentsException extends Exception {
	private static final long serialVersionUID = -2126215907619514897L;

	public InvalidTradeArgumentsException(String message) {
		super(message);
	}
}
