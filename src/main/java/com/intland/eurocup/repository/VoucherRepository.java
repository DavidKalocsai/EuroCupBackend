package com.intland.eurocup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intland.eurocup.model.Voucher;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

}
