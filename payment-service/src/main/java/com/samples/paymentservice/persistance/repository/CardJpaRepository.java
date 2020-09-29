package com.samples.paymentservice.persistance.repository;

import com.samples.paymentservice.persistance.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Elham
 * @since 6/11/2020
 */

@Repository
public interface CardJpaRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
}
