package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.ShoeModel;
import com.Steprella.Steprella.services.dtos.requests.shoemodels.AddShoeModelRequest;
import com.Steprella.Steprella.services.dtos.requests.shoemodels.UpdateShoeModelRequest;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.AddShoeModelResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.ListShoeModelResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.UpdateShoeModelResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ShoeModelMapper {

    ShoeModelMapper INSTANCE = Mappers.getMapper(ShoeModelMapper.class);

    @Mapping(target = "name", source = "modelName")
    @Mapping(target = "brandId", source = "brand.id")
    ListShoeModelResponse listFromShoeModel(ShoeModel shoeModel);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "brand.id", source = "brandId")
    @Mapping(target = "modelName", source = "name")
    @Mapping(target = "productList", ignore = true)
    ShoeModel shoeModelFromAddRequest(AddShoeModelRequest request);

    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "name", source = "modelName")
    AddShoeModelResponse addResponseFromShoeModel(ShoeModel shoeModel);

    @Mapping(target = "brand.id", source = "brandId")
    @Mapping(target = "modelName", source = "name")
    @Mapping(target = "productList", ignore = true)
    ShoeModel shoeModelFromUpdateRequest(UpdateShoeModelRequest request);

    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "name", source = "modelName")
    UpdateShoeModelResponse updateResponseFromShoeModel(ShoeModel shoeModel);
}
