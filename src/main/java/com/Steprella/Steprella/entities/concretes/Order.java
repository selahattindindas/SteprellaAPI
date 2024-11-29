package com.Steprella.Steprella.entities.concretes;

import com.Steprella.Steprella.entities.abstracts.AuditEntity;
import com.Steprella.Steprella.services.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AuditEntity {

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
}
