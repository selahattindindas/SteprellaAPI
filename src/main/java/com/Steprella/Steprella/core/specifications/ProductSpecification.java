package com.Steprella.Steprella.core.specifications;

import com.Steprella.Steprella.entities.concretes.Product;
import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.services.dtos.requests.products.ProductSearchCriteria;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> getFilteredProducts(ProductSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getSearchTerm() != null && !criteria.getSearchTerm().trim().isEmpty()) {
                String searchPattern = "%" + criteria.getSearchTerm().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("description")), searchPattern),
                        cb.like(cb.lower(root.get("brand").get("name")), searchPattern),
                        cb.like(cb.lower(root.get("shoeModel").get("modelName")), searchPattern),
                        cb.like(cb.lower(root.get("category").get("name")), searchPattern)
                ));
            }

            if (criteria.getBrandId() != null) {
                predicates.add(cb.equal(root.get("brand").get("id"), criteria.getBrandId()));
            }

            if (criteria.getCategoryId() != null) {
                predicates.add(cb.or(
                        cb.equal(root.get("category").get("id"), criteria.getCategoryId()),
                        cb.equal(root.get("category").get("parent").get("id"), criteria.getCategoryId())
                ));
            }

            if (criteria.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
            }

            if (criteria.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
            }

            if (criteria.getMaterialId() != null) {
                predicates.add(cb.equal(root.get("material").get("id"), criteria.getMaterialId()));
            }

            if (criteria.getUsageAreaId() != null) {
                predicates.add(cb.equal(root.get("usageArea").get("id"), criteria.getUsageAreaId()));
            }

            if (criteria.getFeatureIds() != null && !criteria.getFeatureIds().isEmpty()) {
                predicates.add(root.join("features").get("id").in(criteria.getFeatureIds()));
            }

            if (criteria.getColorId() != null) {
                // Alt sorgu oluştur
                Subquery<Long> colorSubquery = query.subquery(Long.class);
                Root<Product> subRoot = colorSubquery.from(Product.class);
                Join<Product, ProductVariant> variantJoin = subRoot.join("productVariants");
                
                colorSubquery.select(subRoot.get("id"))
                    .where(cb.and(
                        cb.equal(variantJoin.get("color").get("id"), criteria.getColorId()),
                        cb.equal(subRoot.get("id"), root.get("id"))
                    ));
                
                predicates.add(cb.exists(colorSubquery));
            }

            if (criteria.getSizeValue() != null) {
                predicates.add(cb.equal(
                    root.join("productVariants").join("productSizes").get("sizeValue"), 
                    criteria.getSizeValue()
                ));
            }

            // Join işlemlerinden kaynaklı tekrar eden sonuçları önle
            if (criteria.getColorId() != null || criteria.getSizeValue() != null) {
                query.distinct(true);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
