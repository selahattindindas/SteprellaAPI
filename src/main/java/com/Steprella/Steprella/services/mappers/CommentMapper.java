package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Comment;
import com.Steprella.Steprella.services.dtos.requests.comments.AddCommentRequest;
import com.Steprella.Steprella.services.dtos.requests.comments.UpdateCommentRequest;
import com.Steprella.Steprella.services.dtos.responses.comments.AddCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.UpdateCommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "updatedDate", source = "updatedDate")
    ListCommentResponse listResponseFromComment(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    Comment commentFromAddRequest(AddCommentRequest request);

    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "userId", source = "user.id")
    AddCommentResponse addResponseComment(Comment comment);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    Comment commentFromUpdateRequest(UpdateCommentRequest request);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "userId", source = "user.id")
    UpdateCommentResponse updateResponseComment(Comment comment);
}
