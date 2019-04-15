package com.intland.eurocup.service.lot.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.repository.VoucherRepository;
import com.intland.eurocup.service.DefaultRedeemService;

/**
 * Base Draw strategy.
 */
public abstract class BaseDrawStrategy implements DrawStrategy {
	private Logger logger = LoggerFactory.getLogger(BaseDrawStrategy.class);
	
	private final int allTimePrizeLimit;
	private final int dailyPrizeLimit;
	private final Long winningSequence;

	@Autowired
	private VoucherRepository voucherRepository;
	
	public BaseDrawStrategy(final int allTimePrizeLimit, final int dailyPrizeLimit, final Long winningSequence) {
		this.allTimePrizeLimit = allTimePrizeLimit;
		this.dailyPrizeLimit = dailyPrizeLimit;
		this.winningSequence = winningSequence;
	}

	@Override
	public void draw(final Voucher voucher) {
		logger.info("Before draw! Voucher: " + voucher);
		if (isDrawRequired(voucher)) {
			proceedWithDraw(voucher);
		}
		setAsLoserIfLotStatusNotSet(voucher);
		logger.info("After draw! Voucher: " + voucher);
	}

	private boolean isDrawRequired(final Voucher voucher) {
		return voucherNotYetDrawn(voucher) && allTimeLimitNotReached(voucher)
				&& dailyLimitNotReached(voucher);
	}

	private boolean voucherNotYetDrawn(final Voucher voucher) {
		return voucher.getLotStatus() == LotStatus.NO_DRAW;
	}

	private boolean allTimeLimitNotReached(final Voucher voucher) {
		return voucherRepository.countWinners(voucher.getTerritory().getDbCode()) < allTimePrizeLimit;
	}

	private boolean dailyLimitNotReached(final Voucher voucher) {
		return voucherRepository.countWinnersOnDate(voucher.getCreatedAt(), voucher.getTerritory().getDbCode()) < dailyPrizeLimit;
	}
	
	private void proceedWithDraw(final Voucher voucher) {
		final Long voucherDailySequenceNumber = voucherRepository.countVouchersOnDate(voucher.getCreatedAt(), voucher.getId(), voucher.getTerritory().getDbCode());
		if (voucherDailySequenceNumber % winningSequence == 0) {
			voucher.setLotStatus(LotStatus.WINNER);
		}
	}	
	
	private void setAsLoserIfLotStatusNotSet(final Voucher voucher) {
		if (voucher != null && voucherNotYetDrawn(voucher)) {
			voucher.setLotStatus(LotStatus.LOSER);
		}
	}
}
