package com.Steprella.Steprella.services.abstracts;
import com.Steprella.Steprella.services.dtos.requests.features.AddFeatureRequest;
import com.Steprella.Steprella.services.dtos.requests.features.UpdateFeatureRequest;
import com.Steprella.Steprella.services.dtos.responses.features.AddFeatureResponse;
import com.Steprella.Steprella.services.dtos.responses.features.ListFeatureResponse;
import com.Steprella.Steprella.services.dtos.responses.features.UpdateFeatureResponse;

import java.util.List;

public interface FeatureService {

    List<ListFeatureResponse> getAll(Integer page, Integer size);

    ListFeatureResponse getById(int id);

    int getTotalCount();

    AddFeatureResponse add(AddFeatureRequest request);

    UpdateFeatureResponse update(UpdateFeatureRequest request);

    void delete(int id);
}
