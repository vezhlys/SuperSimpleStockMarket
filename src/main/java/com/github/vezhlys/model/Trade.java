package com.github.vezhlys.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.github.vezhlys.model.enums.TradeType;

/**
 * Trade model.
 */
public class Trade {
	private String tickerId;
	private TradeType type;
	private LocalDateTime timestamp;
	private int quantity;
	private BigDecimal price;

	public String getTickerId() {
		return tickerId;
	}

	public void setTickerId(String tickerId) {
		this.tickerId = tickerId;
	}

	public TradeType getType() {
		return type;
	}

	public void setType(TradeType type) {
		this.type = type;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getCost() {
		return price.multiply(BigDecimal.valueOf(quantity));
	}
}
