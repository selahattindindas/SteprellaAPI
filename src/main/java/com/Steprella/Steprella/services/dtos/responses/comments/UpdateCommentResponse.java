package com.Steprella.Steprella.services.dtos.responses.comments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentResponse {

    private int id;

    private String commentText;

    private int productVariantId;

    private int rating;

    private int userId;

    private String updatedDate;
}
