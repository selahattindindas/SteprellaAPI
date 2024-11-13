package com.Steprella.Steprella.services.dtos.responses.comments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListCommentResponse {

    private int id;

    private String commentText;

    private int productId;

    private int userId;

    private String userFirstName;

    private String userLastName;

    private String createdDate;
}
