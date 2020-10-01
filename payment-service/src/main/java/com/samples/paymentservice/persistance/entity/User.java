package com.samples.paymentservice.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Elham
 * @since 9/27/2020
 */
@Entity
@Table(name = "USER", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_1", columnNames = {"USERNAME"}),
        @UniqueConstraint(name = "UK_USER_2", columnNames = {"NATIONAL_CODE"}),
        @UniqueConstraint(name = "UK_USER_3", columnNames = {"MOBILE_NUMBER"})},
        indexes = {
                @Index(name = "USERNAME", columnList = "USERNAME")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(sequenceName = "user_seq", allocationSize = 1, initialValue = 100, name = "USER_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "FIRST_NAME", columnDefinition = "NVARCHAR2(50)", nullable = false)
    @NotNull
    private String firstName;

    @Column(name = "LAST_NAME", columnDefinition = "NVARCHAR2(50)", nullable = false)
    @NotNull
    private String lastName;

    /*todo: username ro ham az cm bebinam.*/
    @Column(name = "USERNAME", columnDefinition = "NVARCHAR2(20)", nullable = false)
    @NotNull
    private String username;

    /*todo*/
    @Column(name = "PASSWORD", columnDefinition = "NVARCHAR2(200)", nullable = false)
    @NotNull
    private String password;

    @Column(name = "MOBILE_NUMBER", length = 15, nullable = false)
    @NotNull
    private String mobileNumber;

    @Column(name = "NATIONAL_CODE", length = 10, nullable = false)
    @NotNull
    private String nationalCode;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATION_DATE", updatable = false)
    private Date creationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    @Fetch(value = FetchMode.JOIN)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Set<Card> cards = new HashSet<>();
}
