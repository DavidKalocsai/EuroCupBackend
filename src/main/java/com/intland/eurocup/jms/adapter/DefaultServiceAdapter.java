package com.intland.eurocup.jms.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intland.eurocup.common.jms.model.MessageFromBackend;
import com.intland.eurocup.common.jms.model.MessageFromFrontend;
import com.intland.eurocup.common.model.LotResult;
import com.intland.eurocup.jms.DefaultJmsService;
import com.intland.eurocup.jms.adapter.converter.MessageConverters;
import com.intland.eurocup.model.LotStatus;
import com.intland.eurocup.model.Voucher;
import com.intland.eurocup.service.RedeemService;
import com.intland.eurocup.service.lot.exception.UnsupportedTerritoryException;
import com.intland.eurocup.service.validation.exception.EmailAlreadyInUseException;
import com.intland.eurocup.service.validation.exception.VoucherAlreadyInUseException;

/**
 * Default implementation of adapter used between message handler and back-end code.
 */
@Service
public class DefaultServiceAdapter implements ServiceAdapter {
	private Logger logger = LoggerFactory.getLogger(DefaultJmsService.class);
	
	@Autowired
	private RedeemService redeemService;
	
	@Autowired
	private MessageConverters jmsModelConverter;

	@Override
	public MessageFromBackend redeem(final MessageFromFrontend incomingMessage) {
		final Voucher voucher = jmsModelConverter.convert(incomingMessage);
		final LotResult lotResult =  redeemVoucher(voucher);
		return jmsModelConverter.convert(incomingMessage.getRequestId(), lotResult);
	}
	
	private LotResult redeemVoucher(final Voucher voucher) {
		LotResult lotResult = LotResult.DEFAULT;
		try {
			final LotStatus lotStatus =  redeemService.redeem(voucher);
			lotResult = jmsModelConverter.convert(lotStatus);
		} catch (final EmailAlreadyInUseException e) {
			logger.warn("EmailAlreadyInUseException: " + voucher);
			lotResult = LotResult.EMAIL_USED;
		} catch (final VoucherAlreadyInUseException e) {
			logger.warn("VoucherAlreadyInUseException: " + voucher);
			lotResult = LotResult.VOUCHER_USED;
		} catch (final UnsupportedTerritoryException e) {
			logger.warn("UnsupportedTerritoryException: " + voucher);
			lotResult = LotResult.TERRITORY_NOT_SUPPORTED;
		} catch (final Exception e) {
			logger.error("Backend exception: " + voucher);
			lotResult = LotResult.ERROR;
		}
		return lotResult;
	}
}
