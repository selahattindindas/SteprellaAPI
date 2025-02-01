package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Feature;
import com.Steprella.Steprella.services.dtos.requests.features.AddFeatureRequest;
import com.Steprella.Steprella.services.dtos.requests.features.UpdateFeatureRequest;
import com.Steprella.Steprella.services.dtos.responses.features.AddFeatureResponse;
import com.Steprella.Steprella.services.dtos.responses.features.ListFeatureResponse;
import com.Steprella.Steprella.services.dtos.responses.features.UpdateFeatureResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FeatureMapper {

    FeatureMapper INSTANCE = Mappers.getMapper(FeatureMapper.class);

    ListFeatureResponse listResponseFromFeature(Feature feature);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Feature featureFromAddRequest(AddFeatureRequest request);

    AddFeatureResponse addResponseFeature(Feature feature);

    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "products", ignore = true)
    Feature featureFromUpdateRequest(UpdateFeatureRequest request);

    @Mapping(target = "id", source = "feature.id")
    UpdateFeatureResponse updateResponseFeature(Feature feature);
}
