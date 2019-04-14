package com.intland.eurocup.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.intland.eurocup.model.Voucher;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
	List<Voucher> findByEmail(String email);

	List<Voucher> findByVoucher(String voucher);

	List<Voucher> findByEmailAndVoucherAndTerritory(String email, String voucher, String territory);

	@Query(value = "SELECT COUNT(id) FROM Voucher v WHERE v.draw_status = 'winner'", nativeQuery = true)
	Long countWinners();
	
	@Query(value = "SELECT COUNT(id) FROM Voucher v WHERE v.draw_status = 'winner' AND v.created = :creationDate", nativeQuery = true)
    Long countWinnersOnGivenDate(@Param("creationDateTime") Date creationDate);
	
	@Query(value = "SELECT MAX(id) FROM euro_cup.voucher v WHERE v.draw_status = 'winner' AND v.created = :creationDate AND v.id < :currentId", nativeQuery = true)
	Long findLastWinnerIdBeforeCurrentIdByGivenDate(@Param("creationDateTime") Date creationDate, @Param("currentId") Long currentId );
	
	@Query(value = "SELECT COUNT(id) FROM euro_cup.voucher v WHERE :currentId > :winnerId AND v.id > :winnerId AND v.id <= :currentId", nativeQuery = true)
	Long countVouchersBetweenTwoId(@Param("winnerId") Long winnerId, @Param("currentId") Long currentId );
	
	@Query(value = "SELECT COUNT(id) FROM euro_cup.voucher v WHERE :currentId > :winnerId AND v.id > :winnerId AND v.id <= :currentId", nativeQuery = true)
	Long countVocuherVouchersBetweenTwoId(@Param("winnerId") Long winnerId, @Param("currentId") Long currentId );
	
	Long countByCreated(Date creationDate);
}
