package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.ProductSizeService;
import com.Steprella.Steprella.services.dtos.requests.productsizes.AddProductSizeRequest;
import com.Steprella.Steprella.services.dtos.requests.productsizes.UpdateProductSizeRequest;
import com.Steprella.Steprella.services.dtos.responses.productsizes.AddProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.UpdateProductSizeResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductSizeServiceImpl implements ProductSizeService {

    @Override
    public List<ListProductSizeResponse> getProductSizesByProductVariantId(int productVariantId) {
        return List.of();
    }

    @Override
    public ListProductSizeResponse getById(int id) {
        return null;
    }

    @Override
    public AddProductSizeResponse add(AddProductSizeRequest request) {
        return null;
    }

    @Override
    public UpdateProductSizeResponse update(UpdateProductSizeRequest request) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
