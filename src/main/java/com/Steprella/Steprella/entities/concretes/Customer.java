package com.Steprella.Steprella.entities.concretes;

import com.Steprella.Steprella.entities.abstracts.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<BankCard> bankCards;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Cart cart;
}
