package com.intland.eurocup.service.lot.strategy.model;

import com.intland.eurocup.common.model.Territory;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class BaseDrawStrategyData {
	final int allTimeLimit;
	final int dailyLimit;
	final Long winningSequence;
	final Territory supportedTerritory;
}
