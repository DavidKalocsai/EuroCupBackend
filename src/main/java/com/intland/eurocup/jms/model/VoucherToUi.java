package com.intland.eurocup.jms.model;

import com.intland.eurocup.model.DrawStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class VoucherToUi {
	private String voucher;
	private String email;
	private String territory;
	private DrawStatus drawStatus;
}
