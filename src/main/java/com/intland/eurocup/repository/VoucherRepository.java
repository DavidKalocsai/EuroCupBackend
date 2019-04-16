package com.intland.eurocup.repository;

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

	List<Voucher> findByCode(String code);

	List<Voucher> findByEmailAndCodeAndTerritory(String email, String code, Territory territory);

	@Query(value = "SELECT COUNT(id) FROM Voucher v WHERE v.lot_status = 'WINNER' AND v.territory = :territory", nativeQuery = true)
	Long countWinners(@Param("territory") String territory);
	
	@Query(value = "SELECT COUNT(id) FROM Voucher v WHERE v.lot_status = 'WINNER' AND v.created = :creationDate AND v.territory = :territory", nativeQuery = true)
    Long countWinnersOnDate(@Param("creationDate") String creationDate, @Param("territory") String territory);

	@Query(value = "SELECT COUNT(id) FROM Voucher v WHERE v.created = :creationDate AND v.id <= :currentId AND v.territory = :territory", nativeQuery = true)
	Long countVouchersOnDate(@Param("creationDate") String created, @Param("currentId") Long currentId, @Param("territory") String territory);
}
