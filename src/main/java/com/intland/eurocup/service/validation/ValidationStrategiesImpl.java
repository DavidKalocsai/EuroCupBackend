package com.intland.eurocup.service.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.validation.strategy.ValidationStrategy;

/**
 * Collects all {@link ValidationStrategy} and calls each one-by-one to validate {@link Voucher} when validate called uppon. 
 */
@Service
public class ValidationStrategiesImpl implements ValidationStrategies {
	final List<ValidationStrategy> validationStrategies = new ArrayList<>();
	
	@Autowired
	public ValidationStrategiesImpl(final ValidationStrategy... foundValidationStrategies) {
		if (foundValidationStrategies != null) {
			for (final ValidationStrategy validationStrategy : foundValidationStrategies) {
				this.validationStrategies.add(validationStrategy);
			}
		}
	}
	
	@Override
	public void validate(final Voucher voucher) {
		for(final ValidationStrategy validationStrategy : validationStrategies) {
			validationStrategy.validate(voucher);
		}
	}
}
