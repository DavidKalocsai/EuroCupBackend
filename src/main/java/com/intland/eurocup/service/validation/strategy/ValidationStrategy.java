package com.intland.eurocup.service.validation.strategy;

import org.springframework.stereotype.Service;

import com.intland.eurocup.model.Voucher;

@Service
public interface ValidationStrategy {
	
	void validate(final Voucher voucher);
}
