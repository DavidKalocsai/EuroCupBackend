package com.intland.eurocup.jms;

import com.intland.eurocup.common.jms.model.MessageFromBackend;
import com.intland.eurocup.common.jms.model.MessageFromFrontend;

/**
 * Interface to receive messages from JMS and send messages to JMS.
 */
public interface JmsService {
    /**
     * JMS receiver. It will be called on 'message arrived from JMS' event. 
     * @param message - {@link MessageFromBackend}
     */
	public void receiveMessage(final MessageFromFrontend message);
	
	/**
	 * JMS sender. It will be called after received message (voucher) is saved, drawn. The result of lot will be sent back to JMS.
	 * @param message {@link MessageFromBackend}
	 */
	public void send(final MessageFromBackend message);
}
