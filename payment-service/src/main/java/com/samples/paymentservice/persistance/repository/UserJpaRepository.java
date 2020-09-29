package com.samples.paymentservice.persistance.repository;

import com.samples.paymentservice.persistance.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Elham
 * @since 9/29/2020
 */
public interface UserJpaRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(String username);
}
