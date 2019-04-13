package com.intland.eurocup.jms.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class VoucherFromUi {
	private String voucher;
	private String email;
	private String territory;
}
