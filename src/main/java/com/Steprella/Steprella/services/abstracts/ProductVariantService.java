package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductVariantService {

    List<ListProductVariantResponse> getAll();

    ListProductVariantResponse getById(int id);

    AddProductVariantResponse add(AddProductVariantRequest request);

    UpdateProductVariantResponse update(UpdateProductVariantRequest request);

    List<ListProductVariantResponse> filterProducts(Integer brandId, Integer colorId, Integer categoryId, Integer sizeValue);

    List<ListProductVariantResponse> searchProductVariants(String searchTerm);

    void delete(int id);

    BigDecimal getUnitPriceByProductVariantId(int productVariantId);
}
