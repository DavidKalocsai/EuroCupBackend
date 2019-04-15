package com.intland.eurocup.service.validation.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger logger = LoggerFactory.getLogger(TerritoryValidationStrategy.class);
	
	@Autowired
	private DrawStrategies drawStrategies;
	
	@Override
	public void validate(final Voucher voucher) {
		logger.info("Territory validation: " + voucher);
		if (noStrategyFoundForTerritory(voucher)) {
			throw new UnsupportedTerritoryException();
		}
	}

	private boolean noStrategyFoundForTerritory(final Voucher voucher) {
		return !drawStrategies.isStrategyExist(voucher.getTerritory());
	}
}
