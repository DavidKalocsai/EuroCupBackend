package com.intland.eurocup.service.lot.exception;

/**
 * It is thrown if no draw strategy found for a given territory.
 */
public class UnsupportedTerritoryException extends RuntimeException {
	public UnsupportedTerritoryException() {
		super();
	}
}
