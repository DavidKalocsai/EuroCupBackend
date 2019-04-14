package com.intland.eurocup.service.lot;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.model.Voucher;

/*
 * Interface to access all implemented Draw Strategy.
 */
public interface DrawStrategies {
	/**
	 * Sends the voucher to the right strategy (based on {@link Territory} of the {@link Voucher})
	 * @param voucher {@link Voucher}
	 */
	void draw(Voucher voucher);
	
	/**
	 * Method to find if strategy exists to given {@link Territory}.
	 * @param territory {@link Territory}.
	 * @return true if strategy exists to given territory, else false.
	 */
	boolean isStrategyExist(Territory territory);
}
