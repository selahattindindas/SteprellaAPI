package com.Steprella.Steprella.entities.concretes;

import com.Steprella.Steprella.entities.abstracts.AuditEntity;
import com.Steprella.Steprella.services.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends AuditEntity {

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name="amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name="method", nullable = false)
    private PaymentMethod method;

    @Column(name="card_number", nullable = false)
    private String cardNumber;

    @Column(name="card_holder_name", nullable = false)
    private String cardHolderName;

    @Column(name="expiration_date", nullable = false)
    private String expirationDate;

    @Column(name="cvv", nullable = false)
    private String cvv;
}
