package com.intland.eurocup.service.lot;

import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;

/**
 * Interface to draw voucher using DrawStrategies, update voucher with result, save it to DB, return result of lot.
 */
public interface LotService {
	/**
	 * Lot voucher using DrawStrategies, update voucher with result, save it to DB, return result of lot. 
	 * @param voucher {@link Voucher}
	 * @return {@link LotStatus}
	 */
	LotStatus lot(Voucher voucher);
}
