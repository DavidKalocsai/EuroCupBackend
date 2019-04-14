package com.intland.eurocup.service.lot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.persist.PersistentService;

@Service
public class LotService {
	@Autowired
	private DrawStrategies drawStrategies;
	
	@Autowired
	private PersistentService persistentService;
	
		public LotStatus lot(final Voucher persistedVoucher) {
		drawStrategies.draw(persistedVoucher);
		persistentService.update(persistedVoucher);
		return persistedVoucher.getLotStatus();
	}
}
