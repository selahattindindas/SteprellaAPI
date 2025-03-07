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

    private String fullName;

    private String createdDate;

    private String updatedDate;

    private int rating;
}
