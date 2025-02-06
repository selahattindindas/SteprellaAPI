package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.products.ProductSearchCriteria;
import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductVariantService {

    List<ListProductVariantResponse> getRandomVariants(int count);

    ListProductVariantResponse getById(int id);

    AddProductVariantResponse add(AddProductVariantRequest request);

    UpdateProductVariantResponse update(UpdateProductVariantRequest request);

    List<ListProductVariantResponse> search(ProductSearchCriteria criteria);

    List<ListProductVariantResponse> getByProductId(int productId);

    BigDecimal getUnitPriceByProductVariantId(int productVariantId);

    void delete(int id);

    int getTotalCount();

    int getTotalCount(ProductSearchCriteria criteria);
}

