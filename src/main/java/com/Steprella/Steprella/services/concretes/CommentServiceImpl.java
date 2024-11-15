package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.CommentService;
import com.Steprella.Steprella.services.dtos.requests.comments.AddCommentRequest;
import com.Steprella.Steprella.services.dtos.requests.comments.UpdateCommentRequest;
import com.Steprella.Steprella.services.dtos.responses.comments.AddCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.UpdateCommentResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Override
    public List<ListCommentResponse> getCommentsByProductId(int productId) {
        return List.of();
    }

    @Override
    public ListCommentResponse getById(int id) {
        return null;
    }

    @Override
    public AddCommentResponse add(AddCommentRequest request) {
        return null;
    }

    @Override
    public UpdateCommentResponse update(UpdateCommentRequest request) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
