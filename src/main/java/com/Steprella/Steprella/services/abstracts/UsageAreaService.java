package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.usageareas.AddUsageAreaRequest;
import com.Steprella.Steprella.services.dtos.requests.usageareas.UpdateUsageAreaRequest;
import com.Steprella.Steprella.services.dtos.responses.usageareas.AddUsageAreaResponse;
import com.Steprella.Steprella.services.dtos.responses.usageareas.ListUsageAreaResponse;
import com.Steprella.Steprella.services.dtos.responses.usageareas.UpdateUsageAreaResponse;

import java.util.List;

public interface UsageAreaService {

    List<ListUsageAreaResponse> getAll(Integer page, Integer size);

    ListUsageAreaResponse getById(int id);

    int getTotalCount();

    AddUsageAreaResponse add(AddUsageAreaRequest request);

    UpdateUsageAreaResponse update(UpdateUsageAreaRequest request);

    void delete(int id);
}
