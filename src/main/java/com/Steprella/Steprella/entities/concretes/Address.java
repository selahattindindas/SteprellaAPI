package com.Steprella.Steprella.entities.concretes;

import com.Steprella.Steprella.entities.abstracts.BaseEntity;
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
@Table(name="addresses")
public class Address extends BaseEntity {

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;
}
