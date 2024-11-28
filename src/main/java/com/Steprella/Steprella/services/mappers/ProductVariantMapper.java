package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Category;
import com.Steprella.Steprella.entities.concretes.Comment;
import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.categories.ListCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {CommentMapper.class, CategoryMapper.class})
public interface ProductVariantMapper {

    ProductVariantMapper INSTANCE = Mappers.getMapper(ProductVariantMapper.class);

    @Mapping(target = "productSizes", source = "productSizes")
    @Mapping(target = "productFiles", source = "productFiles")
    @Mapping(target = "colorName", source = "color.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "rating", source = "rating")
    @Mapping(target = "ratingCount", source = "ratingCount")
    @Mapping(target = "description", source = "product.description")
    @Mapping(target = "brandName", source = "product.brand.name")
    @Mapping(target = "shoeModelName", source = "product.shoeModel.modelName")
    @Mapping(target = "productComments", expression = "java(mapComments(productVariant.getComments()))")
    @Mapping(target = "category", expression = "java(getCategoryHierarchy(productVariant.getProduct().getCategory()))")
    ListProductVariantResponse listResponseFromProductVariant(ProductVariant productVariant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "color.id", source = "colorId")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    ProductVariant productVariantFromAddRequest(AddProductVariantRequest request);

    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "color.id", source = "colorId")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    ProductVariant productVariantFromUpdateRequest(UpdateProductVariantRequest request);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "colorId", source = "color.id")
    @Mapping(target = "createdDate", source = "createdDate")
    AddProductVariantResponse addResponseFromProductVariant(ProductVariant productVariant);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "colorId", source = "color.id")
    @Mapping(target = "updatedDate", source = "updatedDate")
    UpdateProductVariantResponse updateResponseFromProductVariant(ProductVariant productVariant);

    default ListCategoryResponse getCategoryHierarchy(Category category) {
        ListCategoryResponse response = CategoryMapper.INSTANCE.listResponseFromCategory(category);

        if (category.getParent() != null) {
            response.setParentId(category.getParent().getId());
            response.setChildren(new ArrayList<>());
            ListCategoryResponse parentResponse = getCategoryHierarchy(category.getParent());
            response.getChildren().add(parentResponse);
        } else {
            response.setChildren(new ArrayList<>());
        }

        return response;
    }

    default List<ListCommentResponse> mapComments(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper.INSTANCE::listResponseFromComment)
                .collect(Collectors.toList());
    }


}
