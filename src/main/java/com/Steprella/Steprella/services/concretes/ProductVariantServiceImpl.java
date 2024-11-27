package com.Steprella.Steprella.services.concretes;
import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Category;
import com.Steprella.Steprella.entities.concretes.ProductFile;
import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.repositories.ProductVariantRepository;
import com.Steprella.Steprella.services.abstracts.*;
import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.categories.ListCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;
import com.Steprella.Steprella.services.mappers.ProductVariantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductFileService productFileService;
    private final ProductService productService;
    private final ColorService colorService;
    private final ProductSizeService productSizeService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @Autowired
    public ProductVariantServiceImpl(@Lazy ProductFileService productFileService,
                                     ProductService productService,
                                     ColorService colorService,
                                     CategoryService categoryService,
                                     CommentService commentService,
                                     ProductSizeService productSizeService,
                                     ProductVariantRepository productVariantRepository){
        this.productVariantRepository = productVariantRepository;
        this.productFileService = productFileService;
        this.colorService = colorService;
        this.productService = productService;
        this.productSizeService = productSizeService;
        this.categoryService = categoryService;
        this.commentService = commentService;
    }

    @Override
    public List<ListProductVariantResponse> getAll() {
        return productVariantRepository.findAll().stream()
                .map(this::createProductVariantResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ListProductVariantResponse getById(int id) {
        ProductVariant productVariant = findProductVariantById(id);
        return createProductVariantResponse(productVariant);
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
                .map(this::createProductVariantResponse)
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

    private ListProductVariantResponse createProductVariantResponse(ProductVariant productVariant) {
        List<ProductFile> files = productFileService.getResponseByProductVariantId(productVariant.getId());
        List<ListCommentResponse> comments = commentService.getCommentsByProductVariantId(productVariant.getId());
        List<ListProductSizeResponse> productSizes = productSizeService.getProductSizesByProductVariantId(productVariant.getId());
        ListCategoryResponse category = categoryService.getCategoryHierarchy(productVariant.getProduct().getCategory().getId());
        double averageRating = calculateAverageRating(comments);
        int totalComments = calculateTotalComments(comments);

        ListProductVariantResponse response = ProductVariantMapper.INSTANCE.listResponseFromProductVariant(productVariant, files, comments, productSizes);
        response.setRating(averageRating);
        response.setRatingCount(totalComments);
        response.setCategory(category);
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

    private double calculateAverageRating(List<ListCommentResponse> comments) {
        if (comments.isEmpty()) {
            return 0.0;
        }
        double average = comments.stream().mapToInt(ListCommentResponse::getRating).average().orElse(0.0);

        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(average);

        formatted = formatted.replace(',', '.');

        return Double.parseDouble(formatted);
    }

    private int calculateTotalComments(List<ListCommentResponse> comments) {
        return comments.size();
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

}
