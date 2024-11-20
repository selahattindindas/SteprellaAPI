package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.ProductSize;
import com.Steprella.Steprella.repositories.ProductSizeRepository;
import com.Steprella.Steprella.services.abstracts.ProductSizeService;
import com.Steprella.Steprella.services.dtos.requests.productsizes.AddProductSizeRequest;
import com.Steprella.Steprella.services.dtos.requests.productsizes.UpdateProductSizeRequest;
import com.Steprella.Steprella.services.dtos.responses.productsizes.AddProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.UpdateProductSizeResponse;
import com.Steprella.Steprella.services.mappers.ProductSizeMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductSizeServiceImpl implements ProductSizeService {

    private final ProductSizeRepository productSizeRepository;

    @Override
    public List<ListProductSizeResponse> getProductSizesByProductVariantId(int productVariantId) {
        List<ProductSize> productSizes = productSizeRepository.findByProductVariantId(productVariantId);
        return productSizes.stream().map(ProductSizeMapper.INSTANCE::listResponseFromProductSize).collect(Collectors.toList());
    }

    @Override
    public ListProductSizeResponse getById(int id) {
        ProductSize productSize = productSizeRepository.findById(id).orElse(null);
        return ProductSizeMapper.INSTANCE.listResponseFromProductSize(productSize);
    }

    @Override
    public AddProductSizeResponse add(AddProductSizeRequest request) {
        ProductSize addProductSize = ProductSizeMapper.INSTANCE.productSizeFromAddRequest(request);
        ProductSize saveProductSize = productSizeRepository.save(addProductSize);

        return ProductSizeMapper.INSTANCE.addResponseFromProductSize(saveProductSize);
    }

    @Override
    public UpdateProductSizeResponse update(UpdateProductSizeRequest request) {
        ProductSize updateProductSize = ProductSizeMapper.INSTANCE.productSizeFromUpdateRequest(request);
        ProductSize saveProductSize = productSizeRepository.save(updateProductSize);

        return ProductSizeMapper.INSTANCE.updateResponseFromProductSize(saveProductSize);
    }

    @Override
    public void delete(int id) {
        ProductSize productSize = productSizeRepository.findById(id).orElse(null);
        assert productSize != null;
        productSizeRepository.delete(productSize);
    }

    @Override
    public List<ProductSize> getResponseByProductVariantId(int productVariantId) {
        return productSizeRepository.findByProductVariantId(productVariantId);
    }
}
