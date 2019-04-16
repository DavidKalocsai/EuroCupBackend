package com.intland.eurocup.service.lot;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.model.VoucherTestModel;
import com.intland.eurocup.repository.VoucherRepository;
import com.intland.eurocup.service.lot.strategy.DrawStrategyHungary;


@RunWith(SpringRunner.class)
public class DrawStrategyHungaryTest {
	
	@TestConfiguration
    static class DrawStrategyHungaryTestContext {
        @Bean
        public DrawStrategyHungary drawStrategy() {
            return new DrawStrategyHungary();
        }
    }

	@Autowired
	private DrawStrategyHungary drawStrategy;
	
	@MockBean
	private VoucherRepository voucherRepository;
	
	@Test
	public void getTypeShouldReturnTerritoryHungary() {
		// Given
		
		// When
		final Territory territory = drawStrategy.getType();
		
		// Then
		Assert.assertEquals(Territory.HUN, territory);	
	}
	
	@Test
	public void drawShouldNotChangeVoucherWhenLotStatusAlreadySet() {
		// Given
		final Voucher voucher = VoucherTestModel.creaeteVoucher(Territory.GER, LotStatus.WINNER);
		
		// When
		drawStrategy.draw(voucher);
				
		// Then
		Assert.assertEquals(LotStatus.WINNER, voucher.getLotStatus());
		Mockito.verify(voucherRepository, Mockito.times(0)).countWinners(Matchers.anyString());
	}
	
	@Test
	public void drawShouldResultLoserWhenAllTimeLimitReached() {
		// Given
		final Date date = new Date();
		final Long allTimeMax = 1000L;
		final Territory territory = Territory.GER;
		final Voucher voucher = VoucherTestModel.creaeteVoucher(territory, LotStatus.NO_DRAW);
		voucher.setCreatedAt(date);
		Mockito.when(voucherRepository.countWinners(territory.getDbCode())).thenReturn(allTimeMax);
		
		// When
		drawStrategy.draw(voucher);
				
		// Then
		Assert.assertEquals(LotStatus.LOSER, voucher.getLotStatus());
		Mockito.verify(voucherRepository, Mockito.times(1)).countWinners(Territory.GER.getDbCode());
		Mockito.verify(voucherRepository, Mockito.times(0)).countWinnersOnDate(voucher.getCreationDate(), Territory.GER.getDbCode());
	}
	
	@Test
	public void drawShouldResultLoserWhenDailyLimitReached() {
		// Given
		final Date date = new Date();
		final Long allTimeMax = 1L;
		final Long dailyMax = 100L;
		final Territory territory = Territory.GER;
		final Voucher voucher = VoucherTestModel.creaeteVoucher(territory, LotStatus.NO_DRAW);
		voucher.setCreatedAt(date);
		Mockito.when(voucherRepository.countWinners(territory.getDbCode())).thenReturn(allTimeMax);
		Mockito.when(voucherRepository.countWinnersOnDate(voucher.getCreationDate(), territory.getDbCode())).thenReturn(dailyMax);
		
		// When
		drawStrategy.draw(voucher);
				
		// Then
		Assert.assertEquals(LotStatus.LOSER, voucher.getLotStatus());
		Mockito.verify(voucherRepository, Mockito.times(1)).countWinners(Territory.GER.getDbCode());
		Mockito.verify(voucherRepository, Mockito.times(1)).countWinnersOnDate(voucher.getCreationDate(), Territory.GER.getDbCode());
	}
	
	@Test
	public void drawShouldResultLoserWhenVoucherSequnceNumberNotWinningSequence() {
		// Given
		final Date date = new Date();
		final Long currentId = 1L;
		final Long allTimeMax = 1L;
		final Long dailyMax = 1L;
		final Long voucherDailySequence = 40L;
		final Territory territory = Territory.GER;
		final Voucher voucher = VoucherTestModel.creaeteVoucher(territory, LotStatus.NO_DRAW);
		voucher.setCreatedAt(date);
		voucher.setId(currentId);
		Mockito.when(voucherRepository.countWinners(territory.getDbCode())).thenReturn(allTimeMax);
		Mockito.when(voucherRepository.countWinnersOnDate(voucher.getCreationDate(), territory.getDbCode())).thenReturn(dailyMax);
		Mockito.when(voucherRepository.countVouchersOnDate(voucher.getCreationDate(), currentId, territory.getDbCode())).thenReturn(voucherDailySequence - 1);
		
		// When
		drawStrategy.draw(voucher);
				
		// Then
		Assert.assertEquals(LotStatus.LOSER, voucher.getLotStatus());
		Mockito.verify(voucherRepository, Mockito.times(1)).countWinners(Territory.GER.getDbCode());
		Mockito.verify(voucherRepository, Mockito.times(1)).countWinnersOnDate(voucher.getCreationDate(), Territory.GER.getDbCode());
		Mockito.verify(voucherRepository, Mockito.times(1)).countVouchersOnDate(voucher.getCreationDate(), currentId, Territory.GER.getDbCode());
	}
	
	@Test
	public void drawShouldResultLoserWhenVoucherSequnceNumberWinningSequence() {
		// Given
		final Date date = new Date();
		final Long currentId = 1L;
		final Long allTimeMax = 1L;
		final Long dailyMax = 1L;
		final Long voucherDailySequence = 40L;
		final Territory territory = Territory.GER;
		final Voucher voucher = VoucherTestModel.creaeteVoucher(territory, LotStatus.NO_DRAW);
		voucher.setCreatedAt(date);
		voucher.setId(currentId);
		Mockito.when(voucherRepository.countWinners(territory.getDbCode())).thenReturn(allTimeMax);
		Mockito.when(voucherRepository.countWinnersOnDate(voucher.getCreationDate(), territory.getDbCode())).thenReturn(dailyMax);
		Mockito.when(voucherRepository.countVouchersOnDate(voucher.getCreationDate(), currentId, territory.getDbCode())).thenReturn(voucherDailySequence);
		
		// When
		drawStrategy.draw(voucher);
				
		// Then
		Assert.assertEquals(LotStatus.WINNER, voucher.getLotStatus());
		Mockito.verify(voucherRepository, Mockito.times(1)).countWinners(Territory.GER.getDbCode());
		Mockito.verify(voucherRepository, Mockito.times(1)).countWinnersOnDate(voucher.getCreationDate(), Territory.GER.getDbCode());
		Mockito.verify(voucherRepository, Mockito.times(1)).countVouchersOnDate(voucher.getCreationDate(), currentId, Territory.GER.getDbCode());
	}
}
