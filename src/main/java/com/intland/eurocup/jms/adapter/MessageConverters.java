package com.intland.eurocup.jms.adapter;

import com.intland.eurocup.common.jms.model.MessageFromBackend;
import com.intland.eurocup.common.jms.model.MessageFromFrontend;
import com.intland.eurocup.common.model.LotResult;
import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;

/**
 * Converters to convert between data used by back end and JMS.
 */
public interface MessageConverters {

	/**
	 * Convert incoming message to voucher.
	 * @param message {@link MessageFromFrontend}
	 * @return {@link Voucher}
	 */
	Voucher convert(MessageFromFrontend message);

	/**
	 * Convert result of voucher redeem to outgoing message.
	 * @param requestId - id to identify request.
	 * @param lotResult - {@link LotResult}.
	 * @return {@link MessageFromBackend}s
	 */
	MessageFromBackend convert(Long requestId, LotResult lotResult);

	/**
	 * Convert result of voucher redeem from back end model to common model. 
	 * @param lotStatus {@link LotStatus}
	 * @return {@link LotResult}
	 */
	LotResult convert(LotStatus lotStatus);
}
