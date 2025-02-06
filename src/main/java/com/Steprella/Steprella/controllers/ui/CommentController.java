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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController extends BaseController {

    private final CommentService commentService;

    @GetMapping("/by-product-id/{productId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<List<ListCommentResponse>>> getCommentByProductId(@PathVariable int productId){
        List<ListCommentResponse> comments = commentService.getCommentsByProductId(productId);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, comments);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<BaseResponse<ListCommentResponse>> getById(@PathVariable int id){
        ListCommentResponse comment = commentService.getById(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, comment);
    }

    @PostMapping("/create-comment")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<BaseResponse<AddCommentResponse>> add(@RequestBody @Valid AddCommentRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        AddCommentResponse addCommentResponse = commentService.add(request);
        return sendResponse(HttpStatus.CREATED, Messages.Success.CUSTOM_SUCCESSFULLY, addCommentResponse);
    }

    @PutMapping("/update-comment")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<BaseResponse<UpdateCommentResponse>> update(@RequestBody @Valid UpdateCommentRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return sendResponse(HttpStatus.BAD_REQUEST, Messages.Error.CUSTOM_BAD_REQUEST, null);
        }

        UpdateCommentResponse updateCommentResponse = commentService.update(request);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, updateCommentResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<BaseResponse<Void>>delete(@PathVariable int id) {
        commentService.delete(id);
        return sendResponse(HttpStatus.OK, Messages.Success.CUSTOM_SUCCESSFULLY, null);
    }
}
