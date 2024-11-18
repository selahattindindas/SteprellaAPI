package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.shoemodels.AddShoeModelRequest;
import com.Steprella.Steprella.services.dtos.requests.shoemodels.UpdateShoeModelRequest;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.AddShoeModelResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.ListShoeModelResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.UpdateShoeModelResponse;

import java.util.List;

public interface ShoeModelService {

    List<ListShoeModelResponse> getAll();

    List<ListShoeModelResponse> getShoeModelsByBrandId(int brandId);

    ListShoeModelResponse getById(int id);

    AddShoeModelResponse add(AddShoeModelRequest request);

    UpdateShoeModelResponse update(UpdateShoeModelRequest request);

    void delete(int id);
}
