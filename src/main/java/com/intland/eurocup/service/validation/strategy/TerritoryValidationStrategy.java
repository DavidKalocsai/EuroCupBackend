package com.intland.eurocup.service.validation.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.lot.DrawStrategies;
import com.intland.eurocup.service.lot.exception.UnsupportedTerritoryException;

@Service
public class TerritoryValidationStrategy implements ValidationStrategy {
	
	@Autowired
	private DrawStrategies drawStrategies;
	
	@Override
	public void validate(final Voucher voucher) {
		if (!drawStrategies.isStrategyExist(voucher.getTerritory())) {
			throw new UnsupportedTerritoryException();
		}
	}
}
