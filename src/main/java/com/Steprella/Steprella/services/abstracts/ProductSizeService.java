package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.productsizes.AddProductSizeRequest;
import com.Steprella.Steprella.services.dtos.requests.productsizes.UpdateProductSizeRequest;
import com.Steprella.Steprella.services.dtos.responses.productsizes.AddProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.UpdateProductSizeResponse;

import java.util.List;

public interface ProductSizeService {

    List<ListProductSizeResponse> getProductSizesByProductId(int productId);

    ListProductSizeResponse getById(int id);

    AddProductSizeResponse add(AddProductSizeRequest request);

    UpdateProductSizeResponse update(UpdateProductSizeRequest request);

    void delete(int id);
}