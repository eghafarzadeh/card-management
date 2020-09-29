package com.samples.paymentservice.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Elham
 * @since 9/27/2020
 */
@Entity
@Table(name = "CARD", uniqueConstraints = {
        @UniqueConstraint(name = "UK_CARD_1", columnNames = {"PAN", "USER_ID"})},
        indexes = {
                @Index(name = "PAN", columnList = "PAN")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARD_SEQ")
    @SequenceGenerator(sequenceName = "card_seq", allocationSize = 1, name = "CARD_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "PAN", nullable = false)
    private String pan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private User user;
}
