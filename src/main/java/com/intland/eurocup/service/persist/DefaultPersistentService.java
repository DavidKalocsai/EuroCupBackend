package com.intland.eurocup.service.persist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;

/**
 * Interface to persist {@link Voucher}. 
 */
@Service
public class DefaultPersistentService implements PersistentService {	
	
	@Autowired
	private VoucherRepository repository;
	
	public Voucher saveIfAbsent(final Voucher voucher) {
		Voucher retVoucher = tryRetrieveFromDB(voucher); 
		return retVoucher != null ? retVoucher :  repository.save(voucher);
	}
	
	private Voucher tryRetrieveFromDB(final Voucher voucher) {
		final List<Voucher> vouchers =  repository.findByEmailAndCodeAndTerritory(voucher.getEmail(), voucher.getCode(), voucher.getTerritory());
		return vouchers.isEmpty() ? null : vouchers.get(0);
	}
	
	public Voucher save(final Voucher voucher) {
		return repository.save(voucher);
	}
}
