package com.Steprella.Steprella.services.dtos.requests.comments;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentRequest {

    @NotNull
    @Min(1)
    private int id;

    @NotBlank
    private String commentText;

    @NotNull
    @Min(1)
    private int productId;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private int rating;

    private Date updatedDate;
}