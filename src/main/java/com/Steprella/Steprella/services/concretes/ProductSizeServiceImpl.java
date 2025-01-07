package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.ProductSize;
import com.Steprella.Steprella.repositories.ProductSizeRepository;
import com.Steprella.Steprella.services.abstracts.ProductSizeService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
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
    private final ProductVariantService productVariantService;

    @Override
    public List<ListProductSizeResponse> getProductSizesByProductVariantId(int productVariantId) {
        productVariantService.getById(productVariantId);

        List<ProductSize> productSizes = productSizeRepository.findByProductVariantId(productVariantId);
        return productSizes.stream().map(ProductSizeMapper.INSTANCE::listResponseFromProductSize).collect(Collectors.toList());
    }

    @Override
    public ListProductSizeResponse getById(int id) {
        ProductSize productSize = findProductSizeById(id);
        return ProductSizeMapper.INSTANCE.listResponseFromProductSize(productSize);
    }

    @Override
    public AddProductSizeResponse add(AddProductSizeRequest request) {
        productVariantService.getById(request.getProductVariantId());

        ProductSize addProductSize = ProductSizeMapper.INSTANCE.productSizeFromAddRequest(request);
        ProductSize savedProductSize = productSizeRepository.save(addProductSize);

        return ProductSizeMapper.INSTANCE.addResponseFromProductSize(savedProductSize);
    }

    @Override
    public UpdateProductSizeResponse update(UpdateProductSizeRequest request) {
        ProductSize productSize = findProductSizeById(request.getId());
        productVariantService.getById(request.getProductVariantId());

        ProductSize updateProductSize = ProductSizeMapper.INSTANCE.productSizeFromUpdateRequest(request);
        updateProductSize.setSizeValue(productSize.getSizeValue());

        ProductSize savedProductSize = productSizeRepository.save(updateProductSize);

        return ProductSizeMapper.INSTANCE.updateResponseFromProductSize(savedProductSize);
    }

    @Override
    public void delete(int id) {
        ProductSize productSize = findProductSizeById(id);
        productSizeRepository.delete(productSize);
    }

    @Override
    public ProductSize save(ProductSize productSize) {
        return productSizeRepository.save(productSize);
    }

    private ProductSize findProductSizeById(int id) {
        return productSizeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_PRODUCT_SIZE_NOT_FOUND));
    }
}
