package com.intland.eurocup.service.validation.strategy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;
import com.intland.eurocup.service.validation.VoucherFinderService;
import com.intland.eurocup.service.validation.exception.VoucherAlreadyInUseException;

@Service
public class VoucherValidationStrategy  implements ValidationStrategy {
	
	@Autowired
	private VoucherRepository repository;
	
	@Autowired
	VoucherFinderService voucherFinderService;
	
	@Override
	public void validate(final Voucher voucher) {
		if ( isVoucherCodeUsedByOtherVoucher(voucher) ) {
			throw new VoucherAlreadyInUseException();
		}
	}

	private boolean isVoucherCodeUsedByOtherVoucher(final Voucher voucher) {
		return voucherFinderService.isVoucherNotPersisted(voucher) && isVoucherUsed(voucher);
	}
	
	private boolean isVoucherUsed(final Voucher voucher) {
		final List<Voucher> vouchers =  repository.findByVoucher(voucher.getVoucher());
		return vouchers.size() > 0;
	}
}
