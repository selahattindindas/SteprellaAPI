package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductVariantService {

    List<ListProductVariantResponse> getByProductId(int productId);

    ListProductVariantResponse getById(int id);

    List<ProductVariant> getAllIsActiveTrue();

    AddProductVariantResponse add(AddProductVariantRequest request);

    UpdateProductVariantResponse update(UpdateProductVariantRequest request);

    BigDecimal getUnitPriceByProductVariantId(int productVariantId);

    void delete(int id);

    int getTotalCount();
}

