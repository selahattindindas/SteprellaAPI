package com.Steprella.Steprella.entities.concretes;

import com.Steprella.Steprella.entities.abstracts.AuditEntity;
import com.Steprella.Steprella.services.enums.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "saved_cards")
public class BankCard extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "title")
    private String title;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;

    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;

    @Column(name="cvv", nullable = false)
    private String cvv;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;
}
