package com.intland.eurocup.service.lot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.persist.PersistentService;

/**
 * Interface to draw voucher using DrawStrategies, update voucher with result, save it to DB, return result of lot.
 */
@Service
public class DefaultLotService implements LotService {
	@Autowired
	private DrawStrategies drawStrategies;

	@Autowired
	private PersistentService persistentService;

	@Override
	public LotStatus lot(final Voucher voucher) {
		drawStrategies.draw(voucher);
		Voucher persistedVoucher = persistentService.update(voucher);
		return persistedVoucher.getLotStatus();
	}
}
