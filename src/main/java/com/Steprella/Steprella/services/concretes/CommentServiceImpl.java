package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.RatingUtils;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Comment;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.entities.concretes.Product;
import com.Steprella.Steprella.repositories.CommentRepository;
import com.Steprella.Steprella.services.abstracts.CommentService;
import com.Steprella.Steprella.services.abstracts.CustomerService;
import com.Steprella.Steprella.services.abstracts.ProductService;
import com.Steprella.Steprella.services.dtos.requests.comments.AddCommentRequest;
import com.Steprella.Steprella.services.dtos.requests.comments.UpdateCommentRequest;
import com.Steprella.Steprella.services.dtos.responses.comments.AddCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.UpdateCommentResponse;
import com.Steprella.Steprella.services.mappers.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final RatingUtils ratingUtils;

    @Override
    public List<ListCommentResponse> getCommentsByProductId(int productId) {
        productService.getById(productId);

        List<Comment> comments = commentRepository.findByProductId(productId);
        return comments.stream().map(CommentMapper.INSTANCE::listResponseFromComment).collect(Collectors.toList());
    }

    @Override
    public ListCommentResponse getById(int id) {
        Comment comment = findCommentById(id);
        return CommentMapper.INSTANCE.listResponseFromComment(comment);
    }

    @Override
    @Transactional
    public AddCommentResponse add(AddCommentRequest request) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        Product product = productService.getResponseById(request.getProductId());

        Comment comment = CommentMapper.INSTANCE.commentFromAddRequest(request, customer);
        comment.setCustomer(customer);
        comment.setProduct(product);

        Comment savedComment = commentRepository.save(comment);
        ratingUtils.handleCommentOperation(product.getId());
        
        return CommentMapper.INSTANCE.addResponseFromComment(savedComment);
    }

    @Override
    @Transactional
    public UpdateCommentResponse update(UpdateCommentRequest request) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        Product product = productService.getResponseById(request.getProductId());
        Comment existingComment = findCommentAndValidateOwnership(request.getId());
        
        Comment comment = CommentMapper.INSTANCE.commentFromUpdateRequest(request, customer);
        comment.setCustomer(existingComment.getCustomer());
        comment.setProduct(product);
        comment.setCreatedDate(existingComment.getCreatedDate());

        Comment savedComment = commentRepository.save(comment);
        ratingUtils.handleCommentOperation(savedComment.getProduct().getId());
        
        return CommentMapper.INSTANCE.updateResponseFromComment(savedComment);
    }

    @Override
    @Transactional
    public void delete(int id) {
        Comment comment = findCommentAndValidateOwnership(id);
        int productId = comment.getProduct().getId();
        
        commentRepository.delete(comment);
        ratingUtils.handleCommentOperation(productId);
    }

    private Comment findCommentById(int id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_COMMENT_NOT_FOUND));
    }

    private Comment findCommentAndValidateOwnership(int id) {
        return findCommentById(id);
    }
}
