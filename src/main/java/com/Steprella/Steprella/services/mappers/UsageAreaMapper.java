package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.UsageArea;
import com.Steprella.Steprella.services.dtos.requests.usageareas.AddUsageAreaRequest;
import com.Steprella.Steprella.services.dtos.requests.usageareas.UpdateUsageAreaRequest;
import com.Steprella.Steprella.services.dtos.responses.usageareas.AddUsageAreaResponse;
import com.Steprella.Steprella.services.dtos.responses.usageareas.ListUsageAreaResponse;
import com.Steprella.Steprella.services.dtos.responses.usageareas.UpdateUsageAreaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsageAreaMapper {

    UsageAreaMapper INSTANCE = Mappers.getMapper(UsageAreaMapper.class);

    ListUsageAreaResponse listResponseFromUsageArea(UsageArea usageArea);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    UsageArea usageAreaFromAddRequest(AddUsageAreaRequest request);

    AddUsageAreaResponse addResponseUsageArea(UsageArea usageArea);

    @Mapping(target = "id", source = "request.id")
    @Mapping(target = "products", ignore = true)
    UsageArea usageAreaFromUpdateRequest(UpdateUsageAreaRequest request);

    @Mapping(target = "id", source = "usageArea.id")
    UpdateUsageAreaResponse updateResponseUsageArea(UsageArea usageArea);
}
