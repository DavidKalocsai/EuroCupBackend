package com.intland.eurocup.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.intland.eurocup.common.jms.model.MessageFromBackend;
import com.intland.eurocup.common.jms.model.MessageFromFrontend;
import com.intland.eurocup.jms.adapter.ServiceAdapter;

/**
 * Implementation to receive messages from JMS and send messages to JMS.
 */
@Component
public class DefaultJmsService implements JmsService {
	@Value("${jms.queue.to.ui.name}")
	private String outGoingQueueName;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ServiceAdapter redeemService;
	
	@Override
	@JmsListener(destination = "${jms.queue.from.ui.name}")
    public void receiveMessage(final MessageFromFrontend message) {
        System.out.println("Received from UI <" + message + ">");
        final MessageFromBackend backendMesage = redeemService.redeem(message);
        send(backendMesage);
    }
	
	@Override
	public void send(final MessageFromBackend message) {
    	jmsTemplate.convertAndSend(outGoingQueueName, message);
	}
}
