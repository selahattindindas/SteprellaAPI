package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Brand;
import com.Steprella.Steprella.services.dtos.requests.brands.AddBrandRequest;
import com.Steprella.Steprella.services.dtos.requests.brands.UpdateBrandRequest;
import com.Steprella.Steprella.services.dtos.responses.brands.AddBrandResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.ListBrandResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.UpdateBrandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandMapper {

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

    ListBrandResponse listResponseFromBrand(Brand brand);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productList", ignore = true)
    @Mapping(target = "shoeModels", ignore = true)
    Brand brandFromAddRequest(AddBrandRequest request);

    AddBrandResponse addResponseBrand(Brand brand);

    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "productList", ignore = true)
    @Mapping(target = "shoeModels", ignore = true)
    Brand brandFromUpdateRequest(UpdateBrandRequest request);

    @Mapping(target = "id", source = "brand.id")
    UpdateBrandResponse updateResponseBrand(Brand brand);
}
