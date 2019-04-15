package com.intland.eurocup.service.validation;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.validation.strategy.ValidationStrategy;

/**
 * Interface to validate voucher through multiple {@link ValidationStrategy}. Issues should be handled by exceptions. 
 */
public interface ValidationStrategies {
	
	/**
	 * Validate {@link Voucher}
	 * @param voucher {@link Voucher}
	 */
	void validate(final Voucher voucher);
}
