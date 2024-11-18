package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.Comment;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.entities.concretes.User;
import com.Steprella.Steprella.repositories.CommentRepository;
import com.Steprella.Steprella.services.abstracts.CommentService;
import com.Steprella.Steprella.services.abstracts.CustomerService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.comments.AddCommentRequest;
import com.Steprella.Steprella.services.dtos.requests.comments.UpdateCommentRequest;
import com.Steprella.Steprella.services.dtos.responses.comments.AddCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.UpdateCommentResponse;
import com.Steprella.Steprella.services.mappers.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final CustomerService customerService;
    private CommentRepository commentRepository;

    @Override
    public List<ListCommentResponse> getCommentsByProductId(int productId) {
     List<Comment> comments = commentRepository.findByProductId(productId);
     return comments.stream().map(CommentMapper.INSTANCE::listResponseFromComment).collect(Collectors.toList());
    }

    @Override
    public ListCommentResponse getById(int id) {
        Comment comment = commentRepository.findById(id).orElse(null);

        return CommentMapper.INSTANCE.listResponseFromComment(comment);
    }

    @Override
    public AddCommentResponse add(AddCommentRequest request) {
        String emailOfLoggedInStaff = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedInUser = (User) userService.loadUserByUsername(emailOfLoggedInStaff);
        Customer customer = customerService.getById(loggedInUser.getId());
        Comment addComment = CommentMapper.INSTANCE.commentFromAddRequest(request, customer);
        Comment saveComment = commentRepository.save(addComment);

        return CommentMapper.INSTANCE.addResponseComment(saveComment);
    }

    @Override
    public UpdateCommentResponse update(UpdateCommentRequest request) {
        String emailOfLoggedInStaff = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User loggedInUser = (User) userService.loadUserByUsername(emailOfLoggedInStaff);
        Customer customer = customerService.getById(loggedInUser.getId());
        Comment updateComment = CommentMapper.INSTANCE.commentFromUpdateRequest(request, customer);
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
