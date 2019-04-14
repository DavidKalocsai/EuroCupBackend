package com.intland.eurocup.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.intland.eurocup.common.jms.model.MessageFromBackend;
import com.intland.eurocup.common.jms.model.MessageFromFrontend;
import com.intland.eurocup.common.model.LotResult;
import com.intland.eurocup.jms.adapter.MessageConverters;
import com.intland.eurocup.jms.adapter.ServiceAdapter;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.RedeemService;
import com.intland.eurocup.service.lot.LotService;

@Component
public class JmsService {
	@Value("${jms.queue.to.ui.name}")
	private String outGoingQueue;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ServiceAdapter redeemService;
	
	@JmsListener(destination = "${jms.queue.from.ui.name}")
    public void receiveMessage(final MessageFromFrontend jmsMessage) {
        System.out.println("Received from UI <" + jmsMessage + ">");
        final MessageFromBackend backendMesage = redeemService.redeem(jmsMessage);
        send(converter.convert(jmsMessage.getRequestId(), voucher));
    }
	
	public void send(final MessageFromBackend voucher) {
    	jmsTemplate.convertAndSend(outGoingQueue, voucher);
	}
}
