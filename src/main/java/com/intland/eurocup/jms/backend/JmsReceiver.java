package com.intland.eurocup.jms.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.intland.eurocup.common.jms.model.BackendJmsMesage;
import com.intland.eurocup.common.jms.model.FrontendJmsMessage;
import com.intland.eurocup.common.model.DrawResult;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.VoucherService;

@Component
public class JmsReceiver {
	
	@Autowired
	private JmsSender sender;
	
	@Autowired
	private VoucherService voucherService;
	
	@JmsListener(destination = "${jms.queue.from.ui.name}")
    public void receiveMessage(final FrontendJmsMessage jmsMessage) {
        System.out.println("Received from UI <" + jmsMessage + ">");
        final Voucher voucher = voucherService.save(convert(jmsMessage));
        sender.send(convert(jmsMessage.getRequestId(), voucher));
    }
	
	private Voucher convert(final FrontendJmsMessage jmsMessage) {
		final Voucher voucher = new Voucher();
		voucher.setEmail(jmsMessage.getEmail());
		voucher.setVoucher(jmsMessage.getVoucher());
		voucher.setTerritory(jmsMessage.getTerritory());
		return voucher;
	}
	
	private BackendJmsMesage convert(final Long requestId, final Voucher voucher) {
		final BackendJmsMesage voucherBackendDTO = new BackendJmsMesage();
		voucherBackendDTO.setRequestId(requestId);
		voucherBackendDTO.setVoucher(voucher.getVoucher());
		voucherBackendDTO.setEmail(voucher.getEmail());
		voucherBackendDTO.setDrawResult(DrawResult.WINNER);
		System.out.println("Sending back to UI <" + voucher + ">");
		return voucherBackendDTO;
	}
}
