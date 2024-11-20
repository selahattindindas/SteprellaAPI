package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.ProductFile;
import com.Steprella.Steprella.entities.concretes.ProductSize;
import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.repositories.ProductVariantRepository;
import com.Steprella.Steprella.services.abstracts.CategoryService;
import com.Steprella.Steprella.services.abstracts.ProductFileService;
import com.Steprella.Steprella.services.abstracts.ProductSizeService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.dtos.requests.productvariants.AddProductVariantRequest;
import com.Steprella.Steprella.services.dtos.requests.productvariants.UpdateProductVariantRequest;
import com.Steprella.Steprella.services.dtos.responses.productvariants.AddProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.UpdateProductVariantResponse;
import com.Steprella.Steprella.services.mappers.ProductVariantMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductFileService productFileService;
    private final ProductSizeService productSizeService;
    private final CategoryService categoryService;

    @Override
    public List<ListProductVariantResponse> getAll() {
        List<ProductVariant> productVariants = productVariantRepository.findAll();
        return productVariants.stream().map(product -> {
            String categoryHierarchy = categoryService.getCategoryHierarchy(product.getProduct().getCategory());

            ListProductVariantResponse response = mapProductVariantToLisProductVariantResponse(product);

            response.setCategoryName(categoryHierarchy);

            return response;
        }).collect(Collectors.toList());
    }

    @Override
    public ListProductVariantResponse getById(int id) {
        ProductVariant productVariant = productVariantRepository.findById(id).orElse(null);
        String categoryHierarchy = categoryService.getCategoryHierarchy(productVariant.getProduct().getCategory());
        List<ProductFile> files = productFileService.getResponseByProductVariantId(productVariant.getId());
        List<ProductSize> productSizes = productSizeService.getResponseByProductVariantId(productVariant.getId());
        ListProductVariantResponse response = ProductVariantMapper.INSTANCE.listResponseFromProductVariant(productVariant, files, productSizes);
        response.setCategoryName(categoryHierarchy);
        return  response;
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

    private ListProductVariantResponse mapProductVariantToLisProductVariantResponse(ProductVariant productVariant) {
        List<ProductFile> files = productFileService.getResponseByProductVariantId(productVariant.getId());
        List<ProductSize> productSizes = productSizeService.getResponseByProductVariantId(productVariant.getId());
        return ProductVariantMapper.INSTANCE.listResponseFromProductVariant(productVariant, files, productSizes);
    }
}
