package com.intland.eurocup.service.validation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;

@Service
public class VoucherFinderService {
	
	@Autowired
	private VoucherRepository repository;
	
	public boolean isVoucherNotPersisted(final Voucher voucher) {
		final List<Voucher> vouchers =  repository.findByEmailAndVoucherAndTerritory(voucher.getEmail(), voucher.getVoucher(), voucher.getTerritory().toString());
		return vouchers.isEmpty();
	}
}
