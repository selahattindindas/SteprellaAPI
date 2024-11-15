package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.brands.AddBrandRequest;
import com.Steprella.Steprella.services.dtos.requests.brands.UpdateBrandRequest;
import com.Steprella.Steprella.services.dtos.responses.brands.AddBrandResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.ListBrandResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.UpdateBrandResponse;

import java.util.List;

public interface BrandService {

    List<ListBrandResponse> getAll();

    ListBrandResponse getById(int id);

    AddBrandResponse add(AddBrandRequest request);

    UpdateBrandResponse update(UpdateBrandRequest request);

    void delete(int id);
}
