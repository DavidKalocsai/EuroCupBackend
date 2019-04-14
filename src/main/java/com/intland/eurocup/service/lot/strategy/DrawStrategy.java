package com.intland.eurocup.service.lot.strategy;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.model.Voucher;

public interface DrawStrategy {
	void draw(Voucher voucher);
	
	Territory getType();
}
