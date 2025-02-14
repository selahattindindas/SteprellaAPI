package com.Steprella.Steprella.controllers.ui;

import com.Steprella.Steprella.controllers.BaseController;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.*;
import com.Steprella.Steprella.services.dtos.requests.comments.AddCommentRequest;
import com.Steprella.Steprella.services.dtos.requests.comments.UpdateCommentRequest;
import com.Steprella.Steprella.services.dtos.responses.BaseResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.AddCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.UpdateCommentResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController extends BaseController {
    private final CommentService commentService;

    @GetMapping("/product/{productId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<List<ListCommentResponse>>> getCommentsByProductId(@PathVariable int productId) {
        List<ListCommentResponse> comments = commentService.getCommentsByProductId(productId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, comments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<ListCommentResponse>> getById(@PathVariable int id) {
        ListCommentResponse comment = commentService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, comment);
    }

    @PostMapping("/create-comment")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<BaseResponse<AddCommentResponse>> add(@RequestBody @Valid AddCommentRequest request) {
        AddCommentResponse comment = commentService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, comment);
    }

    @PutMapping("/update-comment")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<BaseResponse<UpdateCommentResponse>> update(@RequestBody @Valid UpdateCommentRequest request) {
        UpdateCommentResponse comment = commentService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, comment);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<BaseResponse<Void>> delete(@PathVariable int id) {
        commentService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
