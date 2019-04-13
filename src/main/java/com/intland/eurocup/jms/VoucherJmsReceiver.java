package com.intland.eurocup.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.intland.eurocup.jms.model.VoucherFromUi;
import com.intland.eurocup.jms.model.VoucherToUi;
import com.intland.eurocup.model.DrawStatus;

@Component
public class VoucherJmsReceiver {
	
	@Autowired
	private VoucherJmsSender sender;
	
	@JmsListener(destination = "${jms.queue.from.ui.name}")
    public void receiveMessage(final VoucherFromUi voucher) {
        System.out.println("Received <" + voucher + ">");
        sender.send(convert(voucher));
    }
	
	private VoucherToUi convert(final VoucherFromUi voucherFromUi) {
		final VoucherToUi voucherToUi = new VoucherToUi();
		voucherToUi.setVoucher(voucherFromUi.getVoucher());
		voucherToUi.setEmail(voucherFromUi.getEmail());
		voucherToUi.setTerritory(voucherFromUi.getTerritory());
		voucherToUi.setDrawStatus(DrawStatus.WINNER);
		return voucherToUi;
	}
}
