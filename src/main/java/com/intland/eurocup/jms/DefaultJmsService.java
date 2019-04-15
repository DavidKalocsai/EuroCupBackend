package com.intland.eurocup.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Logger logger = LoggerFactory.getLogger(DefaultJmsService.class);
	
	@Value("${jms.queue.to.ui.name}")
	private String outGoingQueueName;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private ServiceAdapter redeemService;
	
	@Override
	@JmsListener(destination = "${jms.queue.from.ui.name}")
    public void receiveMessage(final MessageFromFrontend message) {
        logger.info("Received from UI <" + message + ">");
        final MessageFromBackend backendMesage = redeemService.redeem(message);
        send(backendMesage);
    }
	
	@Override
	public void send(final MessageFromBackend message) {
		logger.info("Sent to UI <" + message + ">");
		jmsTemplate.convertAndSend(outGoingQueueName, message);
	}
}
