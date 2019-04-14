package com.intland.eurocup.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.intland.eurocup.common.model.Territory;
import com.intland.eurocup.model.Voucher;

/**
 * Voucher repository - find, save and look-ups used by draw strategies.
 */
@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	List<Voucher> findByEmail(String email);

	List<Voucher> findByVoucher(String voucher);

	List<Voucher> findByEmailAndVoucherAndTerritory(String email, String voucher, String territory);

	@Query(value = "SELECT COUNT(id) FROM Voucher v WHERE v.lot_status = 'winner' AND v.territory = :territory", nativeQuery = true)
	Long countWinners(Territory territory);
	
	@Query(value = "SELECT COUNT(id) FROM Voucher v WHERE v.lot_status = 'winner' AND v.created = :creationDate AND v.territory = :territory", nativeQuery = true)
    Long countWinnersOnDate(@Param("creationDateTime") Date creationDate, Territory territory);
	
	@Query(value = "SELECT MAX(id) FROM euro_cup.voucher v WHERE v.lot_status = 'winner' AND v.created = :creationDate AND v.id < :currentId AND v.territory = :territory", nativeQuery = true)
	Long findPreviusWinnerOnDate(@Param("creationDateTime") Date creationDate, @Param("currentId") Long currentId, Territory territory);
	
	@Query(value = "SELECT COUNT(id) FROM euro_cup.voucher v WHERE :currentId > :winnerId AND v.id > :winnerId AND v.id <= :currentId AND v.territory = :territory", nativeQuery = true)
	Long countVouchersBetweenTwoId(@Param("winnerId") Long winnerId, @Param("currentId") Long currentId, Territory territory );
	
	@Query(value = "SELECT COUNT(id) FROM Voucher v WHERE v.created = :created AND v.id < id AND v.territory = :territory", nativeQuery = true)
	Long countPreviousVouchersOnDate(Date created, Long id, Territory territory);
}
