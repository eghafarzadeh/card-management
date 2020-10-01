package com.samples.paymentservice.persistance.repository;

import com.samples.paymentservice.persistance.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Elham
 * @since 6/11/2020
 */

@Repository
public interface CardJpaRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {
    Optional<Card> findByIdAndUserId(Long cardId, Long userId);

    Optional<Card> findByPanAndUserId(String pan, Long userId);

    List<Card> findAllByUserId(Long userId);

    Optional<Card> findByUserIdAndPan(Long userId, String pan);
}
