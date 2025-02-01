package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.materials.AddMaterialRequest;
import com.Steprella.Steprella.services.dtos.requests.materials.UpdateMaterialRequest;
import com.Steprella.Steprella.services.dtos.responses.materials.AddMaterialResponse;
import com.Steprella.Steprella.services.dtos.responses.materials.ListMaterialResponse;
import com.Steprella.Steprella.services.dtos.responses.materials.UpdateMaterialResponse;

import java.util.List;

public interface MaterialService {

    List<ListMaterialResponse> getAll(Integer page, Integer size);

    ListMaterialResponse getById(int id);

    int getTotalCount();

    AddMaterialResponse add(AddMaterialRequest request);

    UpdateMaterialResponse update(UpdateMaterialRequest request);

    void delete(int id);
}
