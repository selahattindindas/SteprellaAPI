package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.comments.AddCommentRequest;
import com.Steprella.Steprella.services.dtos.requests.comments.UpdateCommentRequest;
import com.Steprella.Steprella.services.dtos.responses.comments.AddCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.UpdateCommentResponse;

import java.util.List;

public interface CommentService {

    List<ListCommentResponse> getCommentsByProductVariantId(int productVariantId);

    ListCommentResponse getById(int id);

    AddCommentResponse add(AddCommentRequest request);

    UpdateCommentResponse update(UpdateCommentRequest request);

    void delete(int id);
}
