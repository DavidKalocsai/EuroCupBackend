package com.intland.eurocup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;

@Service
public class VoucherValidator {
	
	@Autowired
	private VoucherRepository repository;
	
	public boolean validate(final Voucher voucher) {
		boolean valid = true;
		if (isVoucherNotInDB(voucher)) {
			valid = isEmailNotUsed(voucher) && isVoucherNotUsed(voucher);
		} 
		return valid;
	}
	
	private boolean isVoucherNotInDB(final Voucher voucher) {
		final List<Voucher> vouchers =  repository.findByEmailAndVoucherAndTerritory(voucher.getEmail(), voucher.getVoucher(), voucher.getTerritory());
		return vouchers.isEmpty();
	}
	
	private boolean isEmailNotUsed(final Voucher voucher) {
		final List<Voucher> vouchers =  repository.findByEmail(voucher.getEmail());
		return vouchers.isEmpty();
	}
	
	private boolean isVoucherNotUsed(final Voucher voucher) {
		final List<Voucher> vouchers =  repository.findByVoucher(voucher.getVoucher());
		return vouchers.isEmpty();
	}
}
