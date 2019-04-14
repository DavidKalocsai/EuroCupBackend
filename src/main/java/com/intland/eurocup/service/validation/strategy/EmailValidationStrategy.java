package com.intland.eurocup.service.validation.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;
import com.intland.eurocup.service.validation.VoucherFinderService;
import com.intland.eurocup.service.validation.exception.EmailAlreadyInUseException;

@Service
public class EmailValidationStrategy implements ValidationStrategy {

	@Autowired
	private VoucherRepository repository;

	@Autowired
	VoucherFinderService voucherFinderService;

	@Override
	public void validate(final Voucher voucher) {
		if (isEmailUsedByOtherVoucher(voucher)) {
			throw new EmailAlreadyInUseException();
		}
	}

	private boolean isEmailUsedByOtherVoucher(final Voucher voucher) {
		return voucherFinderService.isVoucherNotPersisted(voucher) && isEmailUsed(voucher);
	}

	private boolean isEmailUsed(final Voucher voucher) {
		final List<Voucher> vouchers = repository.findByEmail(voucher.getEmail());
		return vouchers.size() > 0;
	}
}
