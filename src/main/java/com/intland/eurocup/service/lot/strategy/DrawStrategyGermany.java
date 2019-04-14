package com.intland.eurocup.service.lot.strategy;

import org.springframework.beans.factory.annotation.Autowired;

import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;

/**
 * Draw strategy for Germany.
 */
public class DrawStrategyGermany implements DrawStrategy {
	private static final int NUMBER_OF_BALLS = 25;
	private static final int DAILY_LIMIT = 4;
	private static final Long WINNER_MODULO_NUMBER = 40L; // TODO - modulo would work just fine

	@Autowired
	private VoucherRepository voucherRepository;
	
	@Override
	public void draw(final Voucher voucher) {
		if (voucher != null && voucherNotYetDrawn(voucher)) {
			if (allTimeLimitNotReached(voucher) && dailyLimitNotReached(voucher)) {
				final Long lastWinnerIdBeforeCurrentIdToday = voucherRepository.findLastWinnerIdBeforeCurrentIdByGivenDate(voucher.getCreatedAt(), voucher.getId());
				if (lastWinnerIdBeforeCurrentIdToday != 0) {
					Long numberOfVouchersSinceLastWinner = voucherRepository.countVouchersBetweenTwoId(lastWinnerIdBeforeCurrentIdToday, voucher.getId());
					if (numberOfVouchersSinceLastWinner == WINNER_MODULO_NUMBER) {
						voucher.setDrawStatus(LotStatus.WINNER);
					}
				} else {
					Long vouchersToday = voucherRepository.countByCreated(voucher.getCreatedAt());
					if (vouchersToday == WINNER_MODULO_NUMBER) {
						voucher.setDrawStatus(LotStatus.WINNER);
					}
				}
			}
			if (voucher.getDrawStatus() == LotStatus.NO_DRAW) {
				voucher.setDrawStatus(LotStatus.LOSER);
			}
		}		
	}
	
	private boolean voucherNotYetDrawn(final Voucher voucher) {
		return voucher.getDrawStatus() == LotStatus.NO_DRAW;
	}
	
	private boolean allTimeLimitNotReached(final Voucher voucher) {
		return voucherRepository.countWinners() < NUMBER_OF_BALLS;
	}
	
	private boolean dailyLimitNotReached(final Voucher voucher) {
		return voucherRepository.countWinnersOnGivenDate(voucher.getCreatedAt()) < DAILY_LIMIT;
	}	
}
