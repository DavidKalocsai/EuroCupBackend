package com.intland.eurocup.jms.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.intland.eurocup.common.jms.model.BackendJmsMesage;
import com.intland.eurocup.common.jms.model.FrontendJmsMessage;
import com.intland.eurocup.common.model.result.DrawResult;

@Component
public class JmsReceiver {
	
	@Autowired
	private JmsSender sender;
	
	@JmsListener(destination = "${jms.queue.from.ui.name}")
    public void receiveMessage(final FrontendJmsMessage voucher) {
        System.out.println("Received from UI <" + voucher + ">");
        sender.send(convert(voucher));
    }
	
	private BackendJmsMesage convert(final FrontendJmsMessage voucherFromUi) {
		final BackendJmsMesage voucherBackendDTO = new BackendJmsMesage();
		voucherBackendDTO.setVoucher(voucherFromUi.getVoucher());
		voucherBackendDTO.setEmail(voucherFromUi.getEmail());
		voucherBackendDTO.setTerritory(voucherFromUi.getTerritory());
		voucherBackendDTO.setDrawStatus(DrawResult.WINNER);
		return voucherBackendDTO;
	}
}
