package com.intland.eurocup.jms.adapter;

import com.intland.eurocup.common.jms.model.MessageFromBackend;
import com.intland.eurocup.common.jms.model.MessageFromFrontend;

/**
 * Interface to force adapter usage between message handler and back-end code.
 */
public interface ServiceAdapter {
	
	/**
	 * Redeem voucher: validate, persist and lot.
	 * @param incomingMessage {@link MessageFromFrontend}
	 * @return {@link MessageFromBackend}
	 */
	MessageFromBackend redeem(MessageFromFrontend incomingMessage);
}
