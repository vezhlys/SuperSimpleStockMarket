package com.github.vezhlys.validators;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.vezhlys.exceptions.InvalidStockException;
import com.github.vezhlys.model.Stock;
import com.github.vezhlys.model.enums.StockType;

public class StockValidatorTest {
	private static final String STOCK_TICKER = "AAA";

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	Stock stock;

	@Before
	public void init() {
		stock = new Stock();
	}

	@Test
	public void validateStockEmptyArgument() throws InvalidStockException {
		checkExceptionMessage("Invalid ticker. Can't be empty.");
		StockValidator.validateStock(stock);
	}

	@Test
	public void validateStockLastDividendIsEmpty() throws InvalidStockException {
		checkExceptionMessage("Invalid last dividend value (empty or less than zero).");
		stock.setId(STOCK_TICKER);
		StockValidator.validateStock(stock);
	}

	@Test
	public void validateStockLastDividendIsNegative() throws InvalidStockException {
		checkExceptionMessage("Invalid last dividend value (empty or less than zero).");
		stock.setId(STOCK_TICKER);
		stock.setLastDividend(new BigDecimal(-1));
		StockValidator.validateStock(stock);
	}

	@Test
	public void validateStockFixedDividendIsNegative() throws InvalidStockException {
		checkExceptionMessage("Invalid fixed dividend value (less than zero).");
		setIdAndLastDividend();
		stock.setFixedDividend(new BigDecimal(-1));
		StockValidator.validateStock(stock);
	}

	@Test
	public void validateStockTypeIsNull() throws InvalidStockException {
		checkExceptionMessage("Invalid type. Can't be null value.");
		setIdAndLastDividend();
		StockValidator.validateStock(stock);
	}

	@Test
	public void validateParValueIsEmpty() throws InvalidStockException {
		checkExceptionMessage("Invalid par value (empty, zero or less than zero).");
		setIdLastDividendAndType();
		StockValidator.validateStock(stock);
	}

	@Test
	public void validateParValueIsNegative() throws InvalidStockException {
		checkExceptionMessage("Invalid par value (empty, zero or less than zero).");
		setIdLastDividendAndType();
		stock.setParValue(new BigDecimal(-1));
		StockValidator.validateStock(stock);
	}

	@Test
	public void validatePriceIsEmpty() throws InvalidStockException {
		checkExceptionMessage("Invalid price (empty, zero or less thgn zero).");
		setIdLastDividendAndParValue();
		StockValidator.validateStock(stock);
	}

	@Test
	public void validatePriceIsNegative() throws InvalidStockException {
		checkExceptionMessage("Invalid price (empty, zero or less thgn zero).");
		setIdLastDividendAndParValue();
		stock.setPrice(new BigDecimal(-1));
		StockValidator.validateStock(stock);
	}

	@Test
	public void validateStockIsValid() throws InvalidStockException {
		ExpectedException.none();
		setIdLastDividendAndParValue();
		stock.setPrice(new BigDecimal(1));
		StockValidator.validateStock(stock);
	}

	private void checkExceptionMessage(String message) {
		expectedException.expect(InvalidStockException.class);
		expectedException.expectMessage(message);
	}

	private void setIdAndLastDividend() {
		stock.setId(STOCK_TICKER);
		stock.setLastDividend(BigDecimal.ONE);
	}

	private void setIdLastDividendAndType() {
		setIdAndLastDividend();
		stock.setType(StockType.COMMON);
	}

	private void setIdLastDividendAndParValue() {
		setIdLastDividendAndType();
		stock.setParValue(new BigDecimal(1));
	}
}
