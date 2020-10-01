package com.samples.paymentservice.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Elham
 * @since 9/28/2020
 */
@Entity
@Table(name = "TRANSACTION", indexes = {
        @Index(name = "TRANSACTION_DATE", columnList = "TRANSACTION_DATE")}
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSACTION_SEQ")
    @SequenceGenerator(sequenceName = "transaction_seq", initialValue = 100, allocationSize = 1, name = "TRANSACTION_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "destination", nullable = false, updatable = false)
    private String destination;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "TRANSACTION_DATE", updatable = false)
    private Date transactionDate;

    @Column(name = "REFERENCE_NUMBER", nullable = false, updatable = false)
    private String referenceNumber;

    @Column(name = "SOURCE", nullable = false, updatable = false)
    private String source;

    @Column(name = "AMOUNT", nullable = false, updatable = false)
    private BigDecimal amount;

    @Column(name = "STATUS", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @Column(name = "PAYMENT_CLIENT_NAME", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PaymentClientName paymentClientName;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
