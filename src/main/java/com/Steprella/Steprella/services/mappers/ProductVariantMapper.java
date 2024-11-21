package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.ProductFile;
import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductVariantMapper {

    ProductVariantMapper INSTANCE = Mappers.getMapper(ProductVariantMapper.class);

    @Mapping(target = "productSizes", source = "productSizes")
    @Mapping(target = "productComments", source = "comments")
    @Mapping(target = "productFiles", source = "productFiles")
    @Mapping(target = "colorName", source = "productVariant.color.name")
    @Mapping(target = "createdDate", source = "productVariant.createdDate")
    @Mapping(target = "updatedDate", source = "productVariant.updatedDate")
    @Mapping(target = "price", source = "productVariant.product.price")
    @Mapping(target = "rating", source = "productVariant.rating")
    @Mapping(target = "ratingCount", source = "productVariant.ratingCount")
    @Mapping(target = "description", source = "productVariant.product.description")
    @Mapping(target = "categoryName", source = "productVariant.product.category.name")
    @Mapping(target = "brandName", source = "productVariant.product.brand.name")
    @Mapping(target = "shoeModelName", source = "productVariant.product.shoeModel.modelName")
    ListProductVariantResponse listResponseFromProductVariant(ProductVariant productVariant,
                                                              List<ProductFile> productFiles,
                                                              List<ListCommentResponse> comments,
                                                              List<ListProductSizeResponse> productSizes);

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
}
