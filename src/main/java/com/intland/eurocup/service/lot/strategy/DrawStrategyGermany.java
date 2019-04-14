package com.intland.eurocup.service.lot.strategy;

import org.springframework.beans.factory.annotation.Autowired;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;

/**
 * Draw strategy for Germany.
 */
public class DrawStrategyGermany implements DrawStrategy {
	private static final int NUMBER_OF_BALLS = 25;
	private static final int DAILY_LIMIT = 4;
	private static final Long WINNER_MODULO_NUMBER = 40L;

	@Autowired
	private VoucherRepository voucherRepository;

	@Override
	public void draw(final Voucher voucher) {
		if (isDrawRequired(voucher)) {
			proceedWithDraw(voucher);
		}
		setAsLoserIfLotStatusNotSet(voucher);
	}

	private boolean isDrawRequired(final Voucher voucher) {
		return voucher != null && voucherNotYetDrawn(voucher) && allTimeLimitNotReached(voucher)
				&& dailyLimitNotReached(voucher);
	}

	private boolean voucherNotYetDrawn(final Voucher voucher) {
		return voucher.getLotStatus() == LotStatus.NO_DRAW;
	}

	private boolean allTimeLimitNotReached(final Voucher voucher) {
		return voucherRepository.countWinners(voucher.getTerritory()) < NUMBER_OF_BALLS;
	}

	private boolean dailyLimitNotReached(final Voucher voucher) {
		return voucherRepository.countWinnersOnDate(voucher.getCreatedAt(), voucher.getTerritory()) < DAILY_LIMIT;
	}
	
	private void proceedWithDraw(final Voucher voucher) {
		final Long voucherDailySequenceNumber = voucherRepository.countVouchersOnDate(voucher.getCreatedAt(), voucher.getId(), voucher.getTerritory());
		if (voucherDailySequenceNumber % WINNER_MODULO_NUMBER == 0) {
			voucher.setLotStatus(LotStatus.WINNER);
		}
	}	
	
	private void setAsLoserIfLotStatusNotSet(final Voucher voucher) {
		if (voucherNotYetDrawn(voucher)) {
			voucher.setLotStatus(LotStatus.LOSER);
		}
	}

	@Override
	public Territory getType() {
		return Territory.GER;
	}	
}
