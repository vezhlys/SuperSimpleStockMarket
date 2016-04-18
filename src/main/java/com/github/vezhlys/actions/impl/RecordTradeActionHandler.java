package com.github.vezhlys.actions.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.github.vezhlys.actions.ActionHandler;
import com.github.vezhlys.exceptions.InvalidTradeArgumentsException;
import com.github.vezhlys.model.StockMarket;
import com.github.vezhlys.model.Trade;
import com.github.vezhlys.model.enums.TradeType;
import com.github.vezhlys.validators.TradeActionValidator;

/**
 * Trade command action handler. Records the trade (which updates the stock
 * price).
 */
public class RecordTradeActionHandler implements ActionHandler {
	private Trade trade;
	private StockMarket market = StockMarket.getInstance();

	public RecordTradeActionHandler(final String argumentLine) throws InvalidTradeArgumentsException {
		TradeActionValidator.validateArguments(argumentLine, market);
		this.trade = createTradeFromArguments(argumentLine.split("\\s"));
	}

	private Trade createTradeFromArguments(final String[] arguments) {
		Trade trade = new Trade();
		trade.setTickerId(arguments[1].toUpperCase());
		trade.setType(TradeType.valueOf(arguments[2].toUpperCase()));
		trade.setQuantity(Integer.valueOf(arguments[3]));
		trade.setPrice(BigDecimal.valueOf(Double.valueOf(arguments[4])));
		trade.setTimestamp(LocalDateTime.now());
		return trade;
	}

	@Override
	public void action() {
		market.addTrade(trade);
		System.out.println("Trade operation was successful. Current stock data: ");
		System.out.println(market.getTicker(trade.getTickerId()).toString());
	}

}
