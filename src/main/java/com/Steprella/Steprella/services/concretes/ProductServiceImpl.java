package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.specifications.ProductSpecification;
import com.Steprella.Steprella.core.utils.EntityValidator;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Product;
import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.repositories.ProductRepository;
import com.Steprella.Steprella.services.abstracts.*;
import com.Steprella.Steprella.services.dtos.requests.products.AddProductRequest;
import com.Steprella.Steprella.services.dtos.requests.products.ProductSearchCriteria;
import com.Steprella.Steprella.services.dtos.requests.products.UpdateProductRequest;
import com.Steprella.Steprella.services.dtos.responses.categories.ListCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.products.AddProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.UpdateProductResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.mappers.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ShoeModelService shoeModelService;
    private final BrandService brandService;
    private final EntityValidator entityValidator;

    @Override
    public List<ListProductResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Product> products  = productRepository.findAll(pageable).getContent();

        return products.stream()
                .map(this::createProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListProductResponse> getRandomVariants(int count) {
        List<Product> allProducts = productRepository.findAll();
        Collections.shuffle(allProducts);

        return allProducts.stream()
                .map(product -> {
                    ListProductResponse response = createProductResponse(product);
                    List<ListProductVariantResponse> activeVariants = response.getProductVariants().stream()
                            .filter(ListProductVariantResponse::isActive)
                            .collect(Collectors.toList());

                    if (!activeVariants.isEmpty()) {
                        Collections.shuffle(activeVariants);
                        response.setProductVariants(List.of(activeVariants.getFirst()));
                        return response;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .limit(count)
                .collect(Collectors.toList());
    }
    @Override
    public List<ListProductResponse> filter(ProductSearchCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());

        Page<Product> productPage = productRepository.findAll(
                ProductSpecification.getFilteredProducts(criteria),
                pageable
        );

        return productPage.getContent().stream()
                .map(product -> {
                    ListProductResponse response = createProductResponse(product);
                    List<ListProductVariantResponse> activeVariants = response.getProductVariants().stream()
                            .filter(ListProductVariantResponse::isActive)
                            .collect(Collectors.toList());
                    response.setProductVariants(activeVariants);
                    return activeVariants.isEmpty() ? null : response;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListProductResponse> search(String searchTerm) {
        return productRepository.findAll().stream()
                .filter(product ->
                        product.getDescription().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                product.getBrand().getName().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                product.getCategory().getName().toLowerCase().contains(searchTerm.toLowerCase())
                )
                .map(product -> {
                    ListProductResponse response = createProductResponse(product);
                    List<ListProductVariantResponse> activeVariants = response.getProductVariants().stream()
                            .filter(ListProductVariantResponse::isActive)
                            .collect(Collectors.toList());
                    response.setProductVariants(activeVariants);
                    return activeVariants.isEmpty() ? null : response;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public ListProductResponse getById(int id) {
        Product product = findProductById(id);
        return createProductResponse(product);
    }

    @Override
    public AddProductResponse add(AddProductRequest request) {
        validateProductDependencies(request.getShoeModelId(), request.getCategoryId(), request.getBrandId());
        entityValidator.validateShoeModelBrand(request.getShoeModelId(), request.getBrandId());

        Product addProduct = ProductMapper.INSTANCE.productFromAddRequest(request);
        Product savedProduct = productRepository.save(addProduct);

        return ProductMapper.INSTANCE.addResponseFromProduct(savedProduct);
    }

    @Override
    public UpdateProductResponse update(UpdateProductRequest request) {
        findProductById(request.getId());
        validateProductDependencies(request.getShoeModelId(), request.getCategoryId(), request.getBrandId());
        entityValidator.validateShoeModelBrand(request.getShoeModelId(), request.getBrandId());

        Product updateProduct = ProductMapper.INSTANCE.productFromUpdateRequest(request);
        Product savedProduct = productRepository.save(updateProduct);

        return ProductMapper.INSTANCE.updateResponseFromProduct(savedProduct);
    }

    @Override
    public void delete(int id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    @Override
    public int getTotalCount() {
        return (int) productRepository.count();
    }

    @Override
    public int getTotalCount(ProductSearchCriteria criteria) {
        return (int) productRepository.findAll(ProductSpecification.getFilteredProducts(criteria))
                .stream()
                .flatMap(product -> product.getProductVariants().stream())
                .filter(ProductVariant::isActive)
                .count();
    }

    private ListProductResponse createProductResponse(Product product) {
        ListCategoryResponse category = categoryService.getCategoryHierarchy(product.getCategory().getId());
        ListProductResponse response = ProductMapper.INSTANCE.listResponseFromProduct(product);
        response.setCategory(category);

        return response;
    }

    private Product findProductById(int id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_PRODUCT_NOT_FOUND));
    }

    private void validateProductDependencies(int shoeModelId, int categoryId, int brandId) {
        shoeModelService.getById(shoeModelId);
        categoryService.getById(categoryId);
        brandService.getById(brandId);
    }
}
