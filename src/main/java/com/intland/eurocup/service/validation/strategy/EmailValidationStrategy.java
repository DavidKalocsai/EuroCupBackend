package com.intland.eurocup.service.validation.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;
import com.intland.eurocup.service.validation.exception.EmailAlreadyInUseException;

/**
 * Validates Email. Checks if it is previously used. (Validation only runs if voucher is not found previously in DB).  
 */
@Service
public class EmailValidationStrategy implements ValidationStrategy {

	@Autowired
	private VoucherRepository repository;

	@Override
	public void validate(final Voucher voucher) {
		if (isEmailUsedByOtherVoucher(voucher)) {
			throw new EmailAlreadyInUseException();
		}
	}

	private boolean isEmailUsedByOtherVoucher(final Voucher voucher) {
		final List<Voucher> vouchers = repository.findByEmail(voucher.getEmail());
		return vouchers.size() > 0;
	}
}
