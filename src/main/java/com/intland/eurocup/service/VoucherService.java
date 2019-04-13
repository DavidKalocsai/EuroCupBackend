package com.intland.eurocup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;

@Service
public class VoucherService {
	@Autowired
	private VoucherValidator validator;
	
	@Autowired
	private VoucherRepository repository;
	
	public Voucher save(final Voucher voucher) {
		Voucher retVoucher = getVoucherFromDB(voucher); 
		if (retVoucher == null && validator.validate(voucher)) {
			retVoucher = repository.save(voucher);
		}
		return retVoucher; 
	}
	
	private Voucher getVoucherFromDB(final Voucher voucher) {
		final List<Voucher> vouchers =  repository.findByEmailAndVoucherAndTerritory(voucher.getEmail(), voucher.getVoucher(), voucher.getTerritory());
		return vouchers.isEmpty() ? null : vouchers.get(0);
	}
}
