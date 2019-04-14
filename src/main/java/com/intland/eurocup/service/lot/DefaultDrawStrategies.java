package com.intland.eurocup.service.lot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.lot.exception.UnsupportedTerritoryException;
import com.intland.eurocup.service.lot.strategy.DrawStrategy;

/*
 * It is used to access all implemented Draw Strategy.
 */
public class DefaultDrawStrategies implements DrawStrategies {
	final Map<Territory, DrawStrategy> drawStrategies = new HashMap<>();
	
	/**
	 * Constructor collects all implementations of DrawStrategy.
	 * @param foundDrawStrategies list of {@link DrawStrategy}
	 */
	@Autowired
	public DefaultDrawStrategies(final DrawStrategy... foundDrawStrategies) {
		if (foundDrawStrategies != null) {
			for (final DrawStrategy drawStrategy : foundDrawStrategies) {
				this.drawStrategies.put(drawStrategy.getType(), drawStrategy);
			}
		}
	}
	
	@Override
	public void draw(Voucher voucher) {
		final DrawStrategy drawStrategy = drawStrategies.get(voucher.getTerritory());
		validateStrategy(drawStrategy);
		drawStrategy.draw(voucher);
	}
	
	private void validateStrategy(final DrawStrategy strategy) {
		if (strategy == null) {
			throw new UnsupportedTerritoryException();
		}
	}
	
	@Override
	public boolean isStrategyExist(final Territory territory) {
		return drawStrategies.containsKey(territory);
	}
}
