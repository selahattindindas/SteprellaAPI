package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.Comment;
import com.Steprella.Steprella.repositories.CommentRepository;
import com.Steprella.Steprella.services.abstracts.CommentService;
import com.Steprella.Steprella.services.dtos.requests.comments.AddCommentRequest;
import com.Steprella.Steprella.services.dtos.requests.comments.UpdateCommentRequest;
import com.Steprella.Steprella.services.dtos.responses.comments.AddCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.UpdateCommentResponse;
import com.Steprella.Steprella.services.mappers.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;

    @Override
    public List<ListCommentResponse> getCommentsByProductVariantId(int productVariantId) {
     List<Comment> comments = commentRepository.findByProductVariantId(productVariantId);
     return comments.stream().map(CommentMapper.INSTANCE::listResponseFromComment).collect(Collectors.toList());
    }

    @Override
    public ListCommentResponse getById(int id) {
        Comment comment = commentRepository.findById(id).orElse(null);

        return CommentMapper.INSTANCE.listResponseFromComment(comment);
    }

    @Override
    public AddCommentResponse add(AddCommentRequest request) {
        Comment addComment = CommentMapper.INSTANCE.commentFromAddRequest(request);
        Comment saveComment = commentRepository.save(addComment);

        return CommentMapper.INSTANCE.addResponseComment(saveComment);
    }

    @Override
    public UpdateCommentResponse update(UpdateCommentRequest request) {
        Comment updateComment = CommentMapper.INSTANCE.commentFromUpdateRequest(request);
        Comment saveComment = commentRepository.save(updateComment);

        return CommentMapper.INSTANCE.updateResponseComment(saveComment);
    }

    @Override
    public void delete(int id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        assert comment != null;
        commentRepository.delete(comment);
    }
}
