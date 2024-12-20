package com.Steprella.Steprella.entities.concretes;

import com.Steprella.Steprella.entities.abstracts.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_variants")
public class ProductVariant extends AuditEntity {

    @ManyToOne
    @JoinColumn(name="color_id", nullable = false)
    private Color color;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "rating", nullable = false)
    private double rating = 0.0;

    @Column(name = "rating_count", nullable = false)
    private int ratingCount = 0;

    @OneToMany(mappedBy = "productVariant", cascade = CascadeType.ALL)
    private List<ProductSize> productSizes = new ArrayList<>();

    @OneToMany(mappedBy= "productVariant", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy= "productVariant", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy= "productVariant", cascade = CascadeType.ALL)
    private List<ProductFile> productFiles = new ArrayList<>();
}
