package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Product;
import com.Steprella.Steprella.services.dtos.requests.products.AddProductRequest;
import com.Steprella.Steprella.services.dtos.requests.products.UpdateProductRequest;
import com.Steprella.Steprella.services.dtos.responses.products.AddProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.UpdateProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "shoeModelName", source = "shoeModel.modelName")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "updatedDate", source = "updatedDate")
    ListProductResponse listResponseFromProduct(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "shoeModel.id", source = "shoeModelId")
    @Mapping(target = "brand.id", source = "brandId")
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    Product productFromAddRequest(AddProductRequest request);

    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "shoeModelId", source = "shoeModel.id")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "categoryId", source = "category.id")
    AddProductResponse addResponseFromProduct(Product product);

    @Mapping(target = "shoeModel.id", source = "shoeModelId")
    @Mapping(target = "brand.id", source = "brandId")
    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    Product productFromUpdateRequest(UpdateProductRequest request);

    @Mapping(target = "shoeModelId", source = "shoeModel.id")
    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "categoryId", source = "category.id")
    UpdateProductResponse updateResponseFromProduct(Product product);
}
