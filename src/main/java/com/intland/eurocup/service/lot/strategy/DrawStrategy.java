package com.intland.eurocup.service.lot.strategy;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.model.Voucher;

/**
 * Interface for draw strategy.
 */
public interface DrawStrategy {
	/**
	 * Use to draw voucher - decide if winner or loser based on the strategy.
	 * @param voucher {@link Voucher}
	 */
	void draw(Voucher voucher);
	
	/**
	 * Get which territory is supported by the strategy.
	 * @return {@link Territory}
	 */
	Territory getType();
}
