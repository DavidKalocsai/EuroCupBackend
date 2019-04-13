package com.intland.eurocup.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.intland.eurocup.jms.model.VoucherToUi;
import com.intland.eurocup.model.Voucher;

@Service
public class VoucherJmsSender {
	
	@Value("${jms.queue.to.ui.name}")
	private String queueName;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void send(final VoucherToUi voucher) {
    	jmsTemplate.convertAndSend(queueName, voucher);
	}
}
