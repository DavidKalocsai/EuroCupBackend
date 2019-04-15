package com.intland.eurocup.service.validation.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.lot.DrawStrategies;
import com.intland.eurocup.service.lot.exception.UnsupportedTerritoryException;
import com.intland.eurocup.service.lot.strategy.DrawStrategy;

/**
 * Validates {@link Territory}. Checks if territory has {@link DrawStrategy} is {@link DrawStrategies}.  
 */
@Service
public class TerritoryValidationStrategy implements ValidationStrategy {
	
	@Autowired
	private DrawStrategies drawStrategies;
	
	@Override
	public void validate(final Voucher voucher) {
		if (noStrategyFoundForTerritory(voucher)) {
			throw new UnsupportedTerritoryException();
		}
	}

	private boolean noStrategyFoundForTerritory(final Voucher voucher) {
		return !drawStrategies.isStrategyExist(voucher.getTerritory());
	}
}
