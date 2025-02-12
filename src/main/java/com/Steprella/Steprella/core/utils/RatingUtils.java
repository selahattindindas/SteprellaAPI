package com.Steprella.Steprella.core.utils;

import com.Steprella.Steprella.entities.concretes.Comment;
import com.Steprella.Steprella.entities.concretes.Product;
import com.Steprella.Steprella.services.abstracts.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;

@Component
@AllArgsConstructor
public class RatingUtils {

    private final ProductService productService;

    @Transactional
    public void handleCommentOperation(int productId) {
        Product product = productService.getResponseById(productId);
        List<Comment> comments = product.getComments();

        double averageRating = calculateAverageRating(comments);
        int totalComments = calculateTotalComments(comments);

        product.setRating(averageRating);
        product.setRatingCount(totalComments);

        productService.save(product);
    }

    private static double calculateAverageRating(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return 0.0;
        }

        double average = comments.stream()
                .mapToInt(Comment::getRating)
                .average()
                .orElse(0.0);

        return formatDouble(average);
    }

    private static int calculateTotalComments(List<Comment> comments) {
        return (comments == null) ? 0 : comments.size();
    }

    private static double formatDouble(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(value).replace(',', '.');
        return Double.parseDouble(formatted);
    }
}