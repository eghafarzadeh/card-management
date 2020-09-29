package com.samples.paymentservice.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Elham
 * @since 9/28/2020
 */
@Entity
@Table(name = "TRANSACTION", indexes = {
        @Index(name = "TRANSACTION_DATE", columnList = "TRANSACTION_DATE")
}
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSACTION_SEQ")
    @SequenceGenerator(sequenceName = "transaction_seq", allocationSize = 1, name = "TRANSACTION_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;

    private String destination;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "TRANSACTION_DATE", updatable = false)
    private Date transactionDate;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
