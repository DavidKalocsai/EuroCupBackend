package com.intland.eurocup.service.persist;

import com.intland.eurocup.model.Voucher;

/**
 * Interface to persist {@link Voucher}. 
 */
public interface PersistentService {
	/**
	 * Save or update {@link Voucher}. 
	 * @param voucher {@link Voucher}
	 * @return persisted {@link Voucher}
	 */
	Voucher save(Voucher voucher);
	
	/**
	 * Save voucher only if not yet in DB and retrieve it back to have to full data. 
	 * @param voucher {@link Voucher} - converted from incoming data
	 * @return {@link Voucher} - retrieved from DB
	 */
	Voucher saveIfAbsent(Voucher voucher); 
	
	
}
