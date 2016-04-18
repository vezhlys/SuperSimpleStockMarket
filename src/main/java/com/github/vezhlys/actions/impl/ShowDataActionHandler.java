package com.github.vezhlys.actions.impl;

import com.github.vezhlys.actions.ActionHandler;
import com.github.vezhlys.model.StockMarket;

/**
 * Show command action handler. Show market all share index and all stocks data.
 */
public class ShowDataActionHandler implements ActionHandler {

	private StockMarket market = StockMarket.getInstance();

	private ShowDataActionHandler() {
	}

	@Override
	public void action() {
		System.out.println("GBCE All Share Index: " + market.stockIndex());
		System.out.println(market.showStockData());
	}

	public static ShowDataActionHandler getInstance() {
		return ShowDataActionHandlerSingleton.INSTANCE;
	}

	private static class ShowDataActionHandlerSingleton {
		private static final ShowDataActionHandler INSTANCE = new ShowDataActionHandler();
	}

}
