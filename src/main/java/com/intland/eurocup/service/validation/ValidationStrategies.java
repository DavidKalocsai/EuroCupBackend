package com.intland.eurocup.service.validation;

import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;

@Service
public interface ValidationStrategies {
	void validate(final Voucher voucher);
}
