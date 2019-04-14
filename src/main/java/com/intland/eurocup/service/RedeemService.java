package com.intland.eurocup.service;

import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;


/**
 * Redeem voucher: validate, persist and after lot, return result.
 */
public interface RedeemService {
	/**
	 * Redeem voucher.
	 * @param voucher {@link Voucher}
	 * @return {@link LotStatus}
	 */
	LotStatus redeem(Voucher voucher);
}
