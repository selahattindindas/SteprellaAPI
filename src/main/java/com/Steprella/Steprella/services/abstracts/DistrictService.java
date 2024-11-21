package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.responses.district.ListDistrictResponse;

import java.util.List;

public interface DistrictService {

    List<ListDistrictResponse> getDistrictsByCityId(int cityId);
    ListDistrictResponse getById(int id);
}
