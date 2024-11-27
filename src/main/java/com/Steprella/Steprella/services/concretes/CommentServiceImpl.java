package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Comment;
import com.Steprella.Steprella.repositories.CommentRepository;
import com.Steprella.Steprella.services.abstracts.CommentService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.comments.AddCommentRequest;
import com.Steprella.Steprella.services.dtos.requests.comments.UpdateCommentRequest;
import com.Steprella.Steprella.services.dtos.responses.comments.AddCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.UpdateCommentResponse;
import com.Steprella.Steprella.services.mappers.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ProductVariantService productVariantService;

    @Autowired
    public CommentServiceImpl(@Lazy UserService userService,
                              @Lazy ProductVariantService productVariantService,
                              CommentRepository commentRepository){
        this.userService = userService;
        this.productVariantService = productVariantService;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<ListCommentResponse> getCommentsByProductVariantId(int productVariantId) {
        productVariantService.getById(productVariantId);

        List<Comment> comments = commentRepository.findByProductVariantId(productVariantId);
        return comments.stream().map(CommentMapper.INSTANCE::listResponseFromComment).collect(Collectors.toList());
    }

    @Override
    public ListCommentResponse getById(int id) {
        Comment comment = findCommentById(id);
        return CommentMapper.INSTANCE.listResponseFromComment(comment);
    }

    @Override
    public AddCommentResponse add(AddCommentRequest request) {
        validateCommentDependencies(request.getProductVariantId(), request.getUserId());

        Comment addComment = CommentMapper.INSTANCE.commentFromAddRequest(request);
        Comment savedComment = commentRepository.save(addComment);

        return CommentMapper.INSTANCE.addResponseComment(savedComment);
    }

    @Override
    public UpdateCommentResponse update(UpdateCommentRequest request) {
        findCommentById(request.getId());
        validateCommentDependencies(request.getProductVariantId(), request.getUserId());

        Comment updateComment = CommentMapper.INSTANCE.commentFromUpdateRequest(request);
        Comment savedComment = commentRepository.save(updateComment);

        return CommentMapper.INSTANCE.updateResponseComment(savedComment);
    }

    @Override
    public void delete(int id) {
        Comment comment = findCommentById(id);
        commentRepository.delete(comment);
    }

    private Comment findCommentById(int id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_COMMENT_NOT_FOUND));
    }

    private void validateCommentDependencies(int productVariantId, int userId) {
        productVariantService.getById(productVariantId);
        userService.getResponseById(userId);
    }
}
