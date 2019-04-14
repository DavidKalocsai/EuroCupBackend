package com.intland.eurocup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Status of voucher: lot not yet happened, winner, loser.
 */
@Getter
@AllArgsConstructor
@ToString
public enum LotStatus {
  NO_DRAW("NotDrawn"), WINNER("Winner"), LOSER("Loser");

  private String code;

  public static LotStatus getEnumFromCode(final String code) {
    LotStatus orderStatus = null;
    for (final LotStatus o : LotStatus.values()) {
      if (orderStatus == null && o.getCode().equals(code)) {
        orderStatus = o;
      }
    }
    return orderStatus;
  }
}
