package com.Steprella.Steprella.core.utils;

import com.Steprella.Steprella.entities.concretes.Comment;

import java.text.DecimalFormat;
import java.util.List;

public class RatingUtils {

    public static double calculateAverageRating(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return 0.0;
        }

        double average = comments.stream()
                .mapToInt(Comment::getRating)
                .average()
                .orElse(0.0);

        return formatDouble(average);
    }

    public static int calculateTotalComments(List<Comment> comments) {
        return (comments == null) ? 0 : comments.size();
    }

    private static double formatDouble(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(value).replace(',', '.');
        return Double.parseDouble(formatted);
    }
}
