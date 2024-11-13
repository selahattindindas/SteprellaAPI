package com.Steprella.Steprella.services.dtos.requests.comments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentRequest {

    @NotBlank
    private String commentText;

    @NotNull
    private int productId;

    @NotNull
    private int userId;
}
