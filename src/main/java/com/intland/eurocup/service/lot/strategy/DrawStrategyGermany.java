package com.intland.eurocup.service.lot.strategy;

import org.springframework.stereotype.Service;

import com.intland.eurocup.common.model.Territory;

/**
 * Draw strategy for Germany.
 */
@Service
public class DrawStrategyGermany extends BaseDrawStrategy {
	private static final int ALL_TIME_PRIZE_LIMIT = 10;
	private static final int DAILY_PRIZE_LIMIT = 5;
	private static final Long WINNING_SEQUENCE = 4L;

	public DrawStrategyGermany() {
		super(ALL_TIME_PRIZE_LIMIT, DAILY_PRIZE_LIMIT, WINNING_SEQUENCE);
	}

	@Override
	public Territory getType() {
		return Territory.GER;
	}	
}
