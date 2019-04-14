package com.intland.eurocup.service.persist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;

@Service
public class PersistentService {	
	
	@Autowired
	private VoucherRepository repository;
	
	public Voucher save(final Voucher voucher) {
		Voucher retVoucher = retrieveVoucherFromDBIfInDB(voucher); 
		return saveVoucherIfNotInDB(voucher, retVoucher);
	}
	
	private Voucher retrieveVoucherFromDBIfInDB(final Voucher voucher) {
		final List<Voucher> vouchers =  repository.findByEmailAndVoucherAndTerritory(voucher.getEmail(), voucher.getVoucher(), voucher.getTerritory().toString());
		return vouchers.isEmpty() ? null : vouchers.get(0);
	}
	
	private Voucher saveVoucherIfNotInDB(final Voucher voucher, Voucher retVoucher) {
		if (retVoucher == null) {
			retVoucher = repository.save(voucher);
		}
		return retVoucher;
	}
	
	public Voucher update(final Voucher voucher) {
		return repository.save(voucher);
	}
}
