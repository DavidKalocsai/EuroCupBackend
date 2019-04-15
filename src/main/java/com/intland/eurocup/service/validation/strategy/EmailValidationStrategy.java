package com.intland.eurocup.service.validation.strategy;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;
import com.intland.eurocup.service.lot.strategy.BaseDrawStrategy;
import com.intland.eurocup.service.validation.exception.EmailAlreadyInUseException;

/**
 * Validates Email. Checks if it is previously used. (Validation only runs if voucher is not found previously in DB).  
 */
@Service
public class EmailValidationStrategy implements ValidationStrategy {
	private Logger logger = LoggerFactory.getLogger(EmailValidationStrategy.class);

	@Autowired
	private VoucherRepository repository;

	@Override
	public void validate(final Voucher voucher) {
		logger.info("Email validation: " + voucher);
		if (isEmailUsedByOtherVoucher(voucher)) {
			throw new EmailAlreadyInUseException();
		}
	}

	private boolean isEmailUsedByOtherVoucher(final Voucher voucher) {
		final List<Voucher> vouchers = repository.findByEmail(voucher.getEmail());
		return vouchers.size() > 0;
	}
}
