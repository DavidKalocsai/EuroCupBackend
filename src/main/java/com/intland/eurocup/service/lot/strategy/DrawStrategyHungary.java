package com.intland.eurocup.service.lot.strategy;

import org.springframework.beans.factory.annotation.Autowired;

import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;

/**
 * Draw strategy for Hungary.
 */
public class DrawStrategyHungary {
	private static final int NUMBER_OF_BALLS = 25;
	private static final int DAILY_LIMIT = 4;
	private static final Long WINNER_MODULO_NUMBER = 40L; // TODO - modulo would work just fine

	@Autowired
	private VoucherRepository voucherRepository;
	
	public void draw(final Voucher voucher) {
		if (voucher != null && voucherNotYetDrawn(voucher)) {
			if (allTimeLimitNotReached(voucher) && dailyLimitNotReached(voucher)) {
				final Long lastWinnerIdBeforeCurrentIdToday = voucherRepository.findPreviusWinnerOnDate(voucher.getCreatedAt(), voucher.getId(), voucher.getTerritory());
				if (lastWinnerIdBeforeCurrentIdToday != 0) {
					Long numberOfVouchersSinceLastWinner = voucherRepository.countVouchersBetweenTwoId(lastWinnerIdBeforeCurrentIdToday, voucher.getId(), voucher.getTerritory());
					if (numberOfVouchersSinceLastWinner == WINNER_MODULO_NUMBER) {
						voucher.setLotStatus(LotStatus.WINNER);
					}
				} else {
					Long vouchersToday = voucherRepository.countByCreatedAndTerritory(voucher.getCreatedAt(), voucher.getTerritory());
					if (vouchersToday == WINNER_MODULO_NUMBER) {
						voucher.setLotStatus(LotStatus.WINNER);
					}
				}
			}
			if (voucher.getLotStatus() == LotStatus.NO_DRAW) {
				voucher.setLotStatus(LotStatus.LOSER);
			}
		}		
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
}
