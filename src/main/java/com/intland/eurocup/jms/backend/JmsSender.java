package com.intland.eurocup.jms.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.intland.eurocup.common.jms.model.BackendJmsMesage;

@Service
public class JmsSender {
	
	@Value("${jms.queue.to.ui.name}")
	private String queueName;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	public void send(final BackendJmsMesage voucher) {
    	jmsTemplate.convertAndSend(queueName, voucher);
	}
}
