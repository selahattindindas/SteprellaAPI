package com.Steprella.Steprella.services.concretes;
import com.Steprella.Steprella.entities.concretes.ProductFile;
import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.repositories.ProductVariantRepository;
import com.Steprella.Steprella.services.abstracts.*;
import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.comments.ListCommentResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;
import com.Steprella.Steprella.services.mappers.ProductVariantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductFileService productFileService;
    private final ProductSizeService productSizeService;
    private final CategoryService categoryService;
    private final CommentService commentService;

    @Override
    public List<ListProductVariantResponse> getAll() {
        List<ProductVariant> productVariants = productVariantRepository.findAll();

        return productVariants.stream().map(product -> {
            ListProductVariantResponse response = createProductVariantResponse(product);

            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public ListProductVariantResponse getById(int id) {
        ProductVariant productVariant = productVariantRepository.findById(id).orElse(null);

        return createProductVariantResponse(productVariant);
    }

    @Override
    public List<ListProductVariantResponse> filterProducts(Integer brandId, Integer colorId, Integer categoryId, Integer sizeId) {
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
                            && Integer.valueOf(productVariant.getProduct().getCategory().getId()).equals(categoryId))
                    .collect(Collectors.toList());
        }

        if (sizeId != null) {
            filteredProductVariants = filteredProductVariants.stream()
                    .filter(productVariant -> productVariant.getProductSizes().stream()
                            .anyMatch(productSize -> Integer.valueOf(productSize.getId()).equals(sizeId)))
                    .collect(Collectors.toList());
        }

        return filteredProductVariants.stream()
                .map(this::createProductVariantResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AddProductVariantResponse add(AddProductVariantRequest request) {
        ProductVariant addProductVariant = ProductVariantMapper.INSTANCE.productVariantFromAddRequest(request);
        ProductVariant saveProductVariant = productVariantRepository.save(addProductVariant);

        return ProductVariantMapper.INSTANCE.addResponseFromProductVariant(saveProductVariant);
    }

    @Override
    public UpdateProductVariantResponse update(UpdateProductVariantRequest request) {
        ProductVariant updateProductVariant = ProductVariantMapper.INSTANCE.productVariantFromUpdateRequest(request);
        ProductVariant saveProductVariant = productVariantRepository.save(updateProductVariant);

        return ProductVariantMapper.INSTANCE.updateResponseFromProductVariant(saveProductVariant);
    }

    @Override
    public void delete(int id) {
        ProductVariant productVariant = productVariantRepository.findById(id).orElse(null);
        assert productVariant != null;
        productVariantRepository.delete(productVariant);
    }

    private ListProductVariantResponse createProductVariantResponse(ProductVariant productVariant) {
        String categoryHierarchy = categoryService.getCategoryHierarchy(productVariant.getProduct().getCategory());
        List<ProductFile> files = productFileService.getResponseByProductVariantId(productVariant.getId());
        List<ListCommentResponse> comments = commentService.getCommentsByProductVariantId(productVariant.getId());
        List<ListProductSizeResponse> productSizes = productSizeService.getProductSizesByProductVariantId(productVariant.getId());

        double averageRating = calculateAverageRating(comments);
        int totalComments = calculateTotalComments(comments);

        ListProductVariantResponse response = ProductVariantMapper.INSTANCE.listResponseFromProductVariant(productVariant, files, comments, productSizes);
        response.setCategoryName(categoryHierarchy);
        response.setRating(averageRating);
        response.setRatingCount(totalComments);

        return response;
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
}
