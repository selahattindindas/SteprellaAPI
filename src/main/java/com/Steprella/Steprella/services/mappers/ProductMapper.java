package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Feature;
import com.Steprella.Steprella.entities.concretes.Product;
import com.Steprella.Steprella.services.dtos.requests.products.AddProductRequest;
import com.Steprella.Steprella.services.dtos.requests.products.UpdateProductRequest;
import com.Steprella.Steprella.services.dtos.responses.products.AddProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.UpdateProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.stream.Collectors;

@Mapper(imports = {Collectors.class, Feature.class})
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "shoeModelName", source = "shoeModel.modelName")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "materialName", source = "material.name")
    @Mapping(target = "usageAreaName", source = "usageArea.name")
    @Mapping(target = "features", source = "features")
    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "updatedDate", source = "updatedDate")
    ListProductResponse listResponseFromProduct(Product product);

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
}
