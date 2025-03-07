package com.Steprella.Steprella.entities.concretes;

import com.Steprella.Steprella.entities.abstracts.AuditEntity;
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
@Table(name = "comments")
public class Comment extends AuditEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name="comment_text")
    private String commentText;

    @Column(name="rating")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
