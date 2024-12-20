package com.Steprella.Steprella.services.concretes;
import com.Steprella.Steprella.core.utils.RatingUtils;
import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Category;
import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.repositories.ProductVariantRepository;
import com.Steprella.Steprella.services.abstracts.*;
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
    public List<ListProductVariantResponse> getAll() {
        List<ProductVariant> productVariants = productVariantRepository.findAll();
        return productVariants.stream()
                .map(this::productVariantResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ListProductVariantResponse getById(int id) {
        ProductVariant productVariant = findProductVariantById(id);
        return productVariantResponse(productVariant);
    }

    @Override
    public List<ListProductVariantResponse> filterProducts(Integer brandId, Integer colorId, Integer categoryId, Integer sizeValue) {
        List<ProductVariant> filteredProductVariants = productVariantRepository.findAll();

        if (brandId != null) {
            filteredProductVariants = filteredProductVariants.stream()
                    .filter(productVariant -> productVariant.getProduct().getBrand() != null
                            && Integer.valueOf(productVariant.getProduct().getBrand().getId()).equals(brandId))
                    .collect(Collectors.toList());
        }

        if (colorId != null) {
            filteredProductVariants = filteredProductVariants.stream()
                    .filter(productVariant -> productVariant.getColor() != null
                            && Integer.valueOf(productVariant.getColor().getId()).equals(colorId))
                    .collect(Collectors.toList());
        }

        if (categoryId != null) {
            filteredProductVariants = filteredProductVariants.stream()
                    .filter(productVariant -> productVariant.getProduct().getCategory() != null
                            && isCategoryOrChild(productVariant.getProduct().getCategory(), categoryId))
                    .collect(Collectors.toList());
        }

        if (sizeValue != null) {
            filteredProductVariants = filteredProductVariants.stream()
                    .filter(productVariant -> productVariant.getProductSizes().stream()
                            .anyMatch(productSize -> Integer.valueOf(productSize.getSizeValue()).equals(sizeValue)
                                    && productSize.isActive()))
                    .collect(Collectors.toList());
        }

        if (filteredProductVariants.isEmpty()) {
            throw new NotFoundException(Messages.Error.CUSTOM_FILTER_PRODUCT_NOT_FOUND);
        }

        return filteredProductVariants.stream()
                .map(this::productVariantResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListProductVariantResponse> searchProductVariants(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAll();
        }

        String lowerCaseSearchTerm = searchTerm.toLowerCase();

        List<ProductVariant> filteredProductVariants = productVariantRepository.findAll();

        filteredProductVariants = filteredProductVariants.stream()
                .filter(productVariant -> isMatch(productVariant, lowerCaseSearchTerm))
                .toList();

        if (filteredProductVariants.isEmpty()) {
            throw new NotFoundException(Messages.Error.CUSTOM_FILTER_PRODUCT_NOT_FOUND);
        }

        return filteredProductVariants.stream()
                .map(this::productVariantResponse)
                .collect(Collectors.toList());
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
        findProductVariantById(request.getId());
        validateProductVariantDependencies(request.getProductId(), request.getColorId());
        validateProductVariantExistence(request.getColorId(), request.getProductId());

        ProductVariant updateProductVariant = ProductVariantMapper.INSTANCE.productVariantFromUpdateRequest(request);
        ProductVariant savedProductVariant = productVariantRepository.save(updateProductVariant);

        return ProductVariantMapper.INSTANCE.updateResponseFromProductVariant(savedProductVariant);
    }

    @Override
    public void delete(int id) {
        ProductVariant productVariant = findProductVariantById(id);
        productVariantRepository.delete(productVariant);
    }

    @Override
    public BigDecimal getUnitPriceByProductVariantId(int productVariantId) {
        ProductVariant productVariant = findProductVariantById(productVariantId);
        return productVariant.getProduct().getPrice();
    }

    private ListProductVariantResponse productVariantResponse(ProductVariant productVariant) {
        double averageRating = RatingUtils.calculateAverageRating(productVariant.getComments());
        int totalComments = RatingUtils.calculateTotalComments(productVariant.getComments());

        ListProductVariantResponse response = ProductVariantMapper.INSTANCE.listResponseFromProductVariant(productVariant);
        response.setRating(averageRating);
        response.setRatingCount(totalComments);

        return response;
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

    private boolean isCategoryOrChild(Category category, Integer categoryId) {
        if (Integer.valueOf(category.getId()).equals(categoryId)) {
            return true;
        }

        Category parent = category.getParent();
        while (parent != null) {
            if (Integer.valueOf(parent.getId()).equals(categoryId)) {
                return true;
            }
            parent = parent.getParent();
        }
        return false;
    }

    private boolean isMatch(ProductVariant productVariant, String searchTerm) {
        String lowerCaseSearchTerm = searchTerm.toLowerCase();

        boolean categoryMatch = productVariant.getProduct().getCategory() != null &&
                productVariant.getProduct().getCategory().getName().toLowerCase().contains(lowerCaseSearchTerm);

        boolean brandMatch = productVariant.getProduct().getBrand() != null &&
                productVariant.getProduct().getBrand().getName().toLowerCase().contains(lowerCaseSearchTerm);

        boolean colorMatch = productVariant.getColor() != null &&
                productVariant.getColor().getName().toLowerCase().contains(lowerCaseSearchTerm);

        boolean shoeModelMatch = productVariant.getProduct().getShoeModel().getModelName().toLowerCase().contains(lowerCaseSearchTerm);

        return categoryMatch || brandMatch || colorMatch || shoeModelMatch;
    }
}
