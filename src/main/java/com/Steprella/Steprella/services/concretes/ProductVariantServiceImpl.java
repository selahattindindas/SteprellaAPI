package com.Steprella.Steprella.services.concretes;
import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.repositories.ProductVariantRepository;
import com.Steprella.Steprella.services.abstracts.*;
import com.Steprella.Steprella.services.dtos.requests.products.ProductSearchCriteria;
import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;
import com.Steprella.Steprella.services.mappers.ProductVariantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductService productService;
    private final ColorService colorService;

    @Override
    public List<ListProductVariantResponse> getByProductId(int productId) {

        List<ProductVariant> productVariants = productVariantRepository.findByProductId(productId);

        if (productVariants.isEmpty()) {
            throw new NotFoundException(Messages.Error.CUSTOM_PRODUCT_NOT_FOUND);
        }

        return productVariants.stream()
                .map(ProductVariantMapper.INSTANCE::listResponseFromProductVariant)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListProductVariantResponse> getRandomVariants(int count) {
        return List.of();
    }

    @Override
    public ListProductVariantResponse getById(int id) {
        ProductVariant productVariant = findProductVariantById(id);
        return ProductVariantMapper.INSTANCE.listResponseFromProductVariant(productVariant);
    }

    @Override
    public AddProductVariantResponse add(AddProductVariantRequest request) {
        validateProductVariantDependencies(request.getProductId(), request.getColorId());
        validateProductVariantExistence(request.getColorId(), request.getProductId());

        ProductVariant addProductVariant = ProductVariantMapper.INSTANCE.productVariantFromAddRequest(request);
        ProductVariant savedProductVariant = productVariantRepository.save(addProductVariant);

        return ProductVariantMapper.INSTANCE.addResponseFromProductVariant(savedProductVariant);
    }

    @Override
    public UpdateProductVariantResponse update(UpdateProductVariantRequest request) {
        ProductVariant productVariant = findProductVariantById(request.getId());
        productService.getById(request.getProductId());

        ProductVariant updateProductVariant = ProductVariantMapper.INSTANCE.productVariantFromUpdateRequest(request);
        updateProductVariant.setColor(productVariant.getColor());

        ProductVariant savedProductVariant = productVariantRepository.save(updateProductVariant);

        return ProductVariantMapper.INSTANCE.updateResponseFromProductVariant(savedProductVariant);
    }

    @Override
    public List<ListProductVariantResponse> search(ProductSearchCriteria criteria) {
        return List.of();
    }

    @Override
    public BigDecimal getUnitPriceByProductVariantId(int productVariantId) {
        ProductVariant productVariant = findProductVariantById(productVariantId);
        return productVariant.getProduct().getPrice();
    }

    @Override
    public void delete(int id) {
        ProductVariant productVariant = findProductVariantById(id);
        productVariantRepository.delete(productVariant);
    }

    @Override
    public int getTotalCount() {
        return (int) productVariantRepository.count();
    }

    @Override
    public int getTotalCount(ProductSearchCriteria criteria) {
        return 0;
    }

    private ProductVariant findProductVariantById(int id) {
        return productVariantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_PRODUCT_NOT_FOUND));
    }

    private void validateProductVariantExistence(int colorId, int productId) {
        boolean exists = productVariantRepository.existsByColorIdAndProductId(colorId, productId);
        if (exists) {
            throw new BusinessException(Messages.Error.PRODUCT_VARIANT_ALREADY_EXISTS);
        }
    }

    private void validateProductVariantDependencies(int productId, int colorId) {
        productService.getById(productId);
        colorService.getById(colorId);
    }
}