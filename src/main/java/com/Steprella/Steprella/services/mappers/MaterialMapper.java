package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Material;
import com.Steprella.Steprella.services.dtos.requests.materials.AddMaterialRequest;
import com.Steprella.Steprella.services.dtos.requests.materials.UpdateMaterialRequest;
import com.Steprella.Steprella.services.dtos.responses.materials.AddMaterialResponse;
import com.Steprella.Steprella.services.dtos.responses.materials.ListMaterialResponse;
import com.Steprella.Steprella.services.dtos.responses.materials.UpdateMaterialResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MaterialMapper {

    MaterialMapper INSTANCE = Mappers.getMapper(MaterialMapper.class);

    ListMaterialResponse listResponseFromMaterial(Material material);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Material materialFromAddRequest(AddMaterialRequest request);

    AddMaterialResponse addResponseMaterial(Material material);

    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "products", ignore = true)
    Material materialFromUpdateRequest(UpdateMaterialRequest request);

    @Mapping(target = "id", source = "material.id")
    UpdateMaterialResponse updateResponseMaterial(Material material);
}
