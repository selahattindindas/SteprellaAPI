package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.productcolors.AddProductColorRequest;
import com.Steprella.Steprella.services.dtos.responses.productcolors.AddProductColorResponse;
import com.Steprella.Steprella.services.dtos.responses.productcolors.ListProductColorResponse;

import java.util.List;

public interface ProductColorService {

    List<ListProductColorResponse> getProductColorsByProductId(int productId);

    ListProductColorResponse getById(int id);

    AddProductColorResponse add(AddProductColorRequest request);

    void delete(int id);
}
