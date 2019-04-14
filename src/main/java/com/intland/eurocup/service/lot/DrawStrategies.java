package com.intland.eurocup.service.lot;

import com.intland.eurocup.model.Territory;
import com.intland.eurocup.model.Voucher;

public interface DrawStrategies {
	void draw(Voucher voucher);
	
	boolean isStrategyExist(Territory territory);
}
