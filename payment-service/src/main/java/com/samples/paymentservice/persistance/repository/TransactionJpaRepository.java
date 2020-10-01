package com.samples.paymentservice.persistance.repository;

import com.samples.paymentservice.persistance.entity.Transaction;
import com.samples.paymentservice.persistance.entity.TransactionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Elham
 * @since 6/11/2020
 */

@Repository
public interface TransactionJpaRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    Optional<Transaction> findByReferenceNumberAndUserId(String referenceNumber, Long userId);

    @Query("SELECT new com.samples.paymentservice.persistance.entity.TransactionReport(t.source, t.status, count(t)) FROM Transaction t WHERE " +
            "t.transactionDate between :from And :to and t.user.id = :userId group by t.source, t.status")
    List<TransactionReport> reportStatusCounts(@Param("from") Date from, @Param("to") Date to, @Param("userId") Long userId);
}
