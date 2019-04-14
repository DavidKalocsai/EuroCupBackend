package com.intland.eurocup.service.lot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.intland.eurocup.model.Territory;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.lot.exception.UnsupportedTerritoryException;
import com.intland.eurocup.service.lot.strategy.DrawStrategy;

public class DrawStrategiesImpl implements DrawStrategies {
	final Map<Territory, DrawStrategy> drawStrategies = new HashMap<>();
	
	@Autowired
	public DrawStrategiesImpl(final DrawStrategy... foundDrawStrategies) {
		if (foundDrawStrategies != null) {
			for (final DrawStrategy drawStrategy : foundDrawStrategies) {
				this.drawStrategies.put(drawStrategy.getType(), drawStrategy);
			}
		}
	}
	
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
	
	public boolean isStrategyExist(final Territory territory) {
		return drawStrategies.containsKey(territory);
	}
}
