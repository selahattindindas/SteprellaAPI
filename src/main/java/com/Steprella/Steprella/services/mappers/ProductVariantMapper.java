package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductVariantMapper {

    ProductVariantMapper INSTANCE = Mappers.getMapper(ProductVariantMapper.class);

    @Mapping(target = "productSizes", source = "productSizes")
    @Mapping(target = "productFiles", source = "productFiles")
    @Mapping(target = "colorName", source = "color.name")
    ListProductVariantResponse listResponseFromProductVariant(ProductVariant productVariant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "color.id", source = "colorId")
    @Mapping(target = "active", constant = "false")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    ProductVariant productVariantFromAddRequest(AddProductVariantRequest request);

    @Mapping(target = "product.id", source = "productId")
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
