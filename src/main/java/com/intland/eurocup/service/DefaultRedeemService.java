package com.intland.eurocup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.lot.LotService;
import com.intland.eurocup.service.persist.PersistentService;
import com.intland.eurocup.service.validation.strategy.ValidationStrategy;


/**
 * Redeem voucher: validate, persist and after lot, return result.
 */
@Service
public class DefaultRedeemService implements RedeemService {
	@Autowired
	private ValidationStrategy validationService;
	
	@Autowired
	private PersistentService persistantService;
	
	@Autowired
	private LotService lotService;
	
	@Override
	public LotStatus redeem(final Voucher voucher) {
		validationService.validate(voucher);
		final Voucher persistedVoucher = persistantService.save(voucher);
		return lotService.lot(persistedVoucher);
	}
}
