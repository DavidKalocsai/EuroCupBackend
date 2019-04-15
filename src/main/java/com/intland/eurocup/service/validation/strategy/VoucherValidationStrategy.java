package com.intland.eurocup.service.validation.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;
import com.intland.eurocup.service.validation.exception.VoucherAlreadyInUseException;

/**
 * Validates voucher code. Checks if it is previously used. (Validation only runs if voucher is not found previously in DB).  
 */
@Service
public class VoucherValidationStrategy  implements ValidationStrategy {
	
	@Autowired
	private VoucherRepository repository;
	
	@Override
	public void validate(final Voucher voucher) {
		if ( isVoucherCodeUsedByOtherVoucher(voucher) ) {
			throw new VoucherAlreadyInUseException();
		}
	}

	private boolean isVoucherCodeUsedByOtherVoucher(final Voucher voucher) {
		final List<Voucher> vouchers =  repository.findByCode(voucher.getCode());
		return vouchers.size() > 0;
	}
}
