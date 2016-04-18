package com.github.vezhlys.exceptions;

/**
 * Exception on creating invalid stock.
 */
public class InvalidStockException extends Exception {
	private static final long serialVersionUID = 7994815376262243599L;

	public InvalidStockException(String message) {
		super(message);
	}
}
