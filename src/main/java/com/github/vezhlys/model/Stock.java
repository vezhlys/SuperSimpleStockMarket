package com.github.vezhlys.model;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import com.github.vezhlys.exceptions.InvalidStockException;
import com.github.vezhlys.model.enums.StockType;
import com.github.vezhlys.validators.StockValidator;

/**
 * Stock model.
 */
public class Stock implements Comparable<Stock> {
	public static final int SCALE = 3;
	private String id;
	private StockType type;
	private BigDecimal lastDividend;
	private BigDecimal fixedDividend;
	private BigDecimal price = BigDecimal.ZERO;
	private BigDecimal parValue;

	public static Stock stockBuilder(String id, StockType type, BigDecimal lastDividend, BigDecimal fixedDividend,
			BigDecimal parValue, BigDecimal price) throws InvalidStockException {
		Stock stock = new Stock();
		stock.setId(id);
		stock.setType(type);
		stock.setLastDividend(lastDividend);
		stock.setFixedDividend(fixedDividend);
		stock.setParValue(parValue);
		stock.setPrice(price);
		StockValidator.validateStock(stock);
		return stock;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
	}

	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(BigDecimal lastDividend) {
		this.lastDividend = lastDividend;
	}

	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(BigDecimal fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getParValue() {
		return parValue;
	}

	public void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	public BigDecimal dividendYield() {
		return StockType.COMMON.equals(getType()) ? getCommonYield() : getPreferredYield();
	}

	public BigDecimal peRatio() {
		return ZERO.compareTo(getLastDividend()) < 0
				? getPrice().divide(getLastDividend().add(dividendYield().multiply(getLastDividend())), SCALE,
						BigDecimal.ROUND_HALF_UP)
				: BigDecimal.ZERO;
	}

	private BigDecimal getCommonYield() {
		return ZERO.compareTo(getPrice()) >= 0 ? ZERO
				: getLastDividend().divide(getPrice(), SCALE, BigDecimal.ROUND_HALF_UP);
	}

	private BigDecimal getPreferredYield() {
		return ZERO.compareTo(getPrice()) >= 0 ? ZERO
				: getFixedDividend().multiply(getParValue()).divide(getPrice(), SCALE, BigDecimal.ROUND_HALF_UP);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return StringUtils.capitalize(id) + "\t" + StringUtils.capitalize(type.toString().toLowerCase()) + "\t"
				+ lastDividend.setScale(SCALE) + "\t"
				+ (fixedDividend != null
						? fixedDividend.multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%"
						: "")
				+ "\t" + parValue.setScale(SCALE) + "\t" + price.setScale(SCALE) + "\t"
				+ dividendYield().multiply(BigDecimal.valueOf(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "%" + "\t"
				+ peRatio();
	}

	@Override
	public int compareTo(Stock o) {
		return this.getId().compareTo(o.getId());
	}
}
