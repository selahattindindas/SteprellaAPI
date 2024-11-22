package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.ProductSize;
import com.Steprella.Steprella.services.dtos.requests.productsizes.AddProductSizeRequest;
import com.Steprella.Steprella.services.dtos.requests.productsizes.UpdateProductSizeRequest;
import com.Steprella.Steprella.services.dtos.responses.productsizes.AddProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.UpdateProductSizeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductSizeMapper {

    ProductSizeMapper INSTANCE = Mappers.getMapper(ProductSizeMapper.class);

    @Mapping(target = "active", expression = "java(productSize.getStockQuantity() > 0)")
    ListProductSizeResponse listResponseFromProductSize(ProductSize productSize);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productVariant.id", source = "productVariantId")
    @Mapping(target = "active", expression = "java(request.getStockQuantity() > 0)")
    ProductSize productSizeFromAddRequest(AddProductSizeRequest request);

    @Mapping(target = "productVariantId", source = "productVariant.id")
    AddProductSizeResponse addResponseFromProductSize(ProductSize productSize);

    @Mapping(target = "productVariant.id", source = "productVariantId")
    @Mapping(target = "active", expression = "java(request.getStockQuantity() > 0)")
    ProductSize productSizeFromUpdateRequest(UpdateProductSizeRequest request);

    @Mapping(target = "productVariantId", source = "productVariant.id")
    UpdateProductSizeResponse updateResponseFromProductSize(ProductSize productSize);
}

