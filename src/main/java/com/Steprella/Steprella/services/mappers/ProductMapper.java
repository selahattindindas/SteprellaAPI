package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.*;
import com.Steprella.Steprella.services.dtos.requests.products.AddProductRequest;
import com.Steprella.Steprella.services.dtos.requests.products.UpdateProductRequest;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.products.AddProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.UpdateProductResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(imports = {CommentMapper.class, Collectors.class, Feature.class, ProductVariantMapper.class})
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "rating", source = "product.rating")
    @Mapping(target = "ratingCount", source = "product.ratingCount")
    @Mapping(target = "shoeModelName", source = "product.shoeModel.modelName")
    @Mapping(target = "category", source = "product.category")
    @Mapping(target = "brandName", source = "product.brand.name")
    @Mapping(target = "materialName", source = "product.material.name")
    @Mapping(target = "usageAreaName", source = "product.usageArea.name")
    @Mapping(target = "features", source = "product.features")
    @Mapping(target = "productVariants", expression = "java(mapProductVariantsWithFavorites(product.getProductVariants(), favoriteVariantIds))")
    @Mapping(target = "productComments", expression = "java(mapComments(product.getComments()))")
    @Mapping(target = "createdDate", source = "product.createdDate")
    @Mapping(target = "updatedDate", source = "product.updatedDate")
    ListProductResponse listResponseFromProduct(Product product, List<Integer> favoriteVariantIds);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shoeModel.id", source = "shoeModelId")
    @Mapping(target = "brand.id", source = "brandId")
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "material.id", source = "materialId")
    @Mapping(target = "usageArea.id", source = "usageAreaId")
    @Mapping(target = "features", expression = "java(request.getFeaturesId().stream().map(id -> { Feature feature = new Feature(); feature.setId(id); return feature; }).collect(Collectors.toList()))")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    Product productFromAddRequest(AddProductRequest request);

    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "shoeModelId", source = "shoeModel.id")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "materialId", source = "material.id")
    @Mapping(target = "usageAreaId", source = "usageArea.id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "featuresId", expression = "java(product.getFeatures().stream().map(Feature::getId).collect(Collectors.toList()))")
    AddProductResponse addResponseFromProduct(Product product);

    @Mapping(target = "shoeModel.id", source = "shoeModelId")
    @Mapping(target = "brand.id", source = "brandId")
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "material.id", source = "materialId")
    @Mapping(target = "usageArea.id", source = "usageAreaId")
    @Mapping(target = "features", expression = "java(request.getFeaturesId().stream().map(id -> { Feature feature = new Feature(); feature.setId(id); return feature; }).collect(Collectors.toList()))")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    Product productFromUpdateRequest(UpdateProductRequest request);

    @Mapping(target = "shoeModelId", source = "shoeModel.id")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "materialId", source = "material.id")
    @Mapping(target = "usageAreaId", source = "usageArea.id")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "featuresId", expression = "java(product.getFeatures().stream().map(Feature::getId).collect(Collectors.toList()))")
    UpdateProductResponse updateResponseFromProduct(Product product);

    default List<ListCommentResponse> mapComments(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper.INSTANCE::listResponseFromComment)
                .collect(Collectors.toList());
    }

    default List<ListProductVariantResponse> mapProductVariantsWithFavorites(List<ProductVariant> variants, List<Integer> favoriteVariantIds) {
        return variants.stream()
                .map(variant -> {
                    ListProductVariantResponse response = ProductVariantMapper.INSTANCE.listResponseFromProductVariant(variant);
                    boolean isFavorite = favoriteVariantIds.contains(variant.getId());
                    response.setFavorite(isFavorite);
                    return response;
                })
                .collect(Collectors.toList());
    }
}
