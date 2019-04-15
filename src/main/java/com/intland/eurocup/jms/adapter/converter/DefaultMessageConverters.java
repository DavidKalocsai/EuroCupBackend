package com.intland.eurocup.jms.adapter.converter;

import org.springframework.stereotype.Service;

import com.intland.eurocup.common.jms.model.MessageFromBackend;
import com.intland.eurocup.common.jms.model.MessageFromFrontend;
import com.intland.eurocup.common.model.LotResult;
import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;

/**
 * Converters to convert between data used by back end and JMS.
 */
@Service
public class DefaultMessageConverters implements MessageConverters {

	@Override
	public Voucher convert(final MessageFromFrontend jmsMessage) {
		final Voucher voucher = new Voucher();
		voucher.setEmail(jmsMessage.getEmail());
		voucher.setCode(jmsMessage.getVoucher());
		voucher.setTerritory(jmsMessage.getTerritory());
		voucher.setLotStatus(LotStatus.NO_DRAW);
		return voucher;
	}

	@Override
	public MessageFromBackend convert(final Long requestId, final LotResult lotResult) {
		final MessageFromBackend backendMessage = new MessageFromBackend();
		backendMessage.setRequestId(requestId);
		backendMessage.setLotResult(lotResult);
		return backendMessage;
	}

	@Override
	public LotResult convert(final LotStatus lotStatus) {
		LotResult lotResult = LotResult.ERROR;
		if (lotStatus != LotStatus.NO_DRAW) {
			lotResult = lotStatus == LotStatus.WINNER ? LotResult.WINNER : LotResult.LOSER;
		}
		return lotResult;
	}
}
