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
@Table(name = "usage_areas")
public class UsageArea extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "usageArea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}
