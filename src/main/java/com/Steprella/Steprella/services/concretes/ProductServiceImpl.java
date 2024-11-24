package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.Product;
import com.Steprella.Steprella.repositories.ProductRepository;
import com.Steprella.Steprella.services.abstracts.CategoryService;
import com.Steprella.Steprella.services.abstracts.ProductService;
import com.Steprella.Steprella.services.abstracts.ShoeModelService;
import com.Steprella.Steprella.services.dtos.requests.products.AddProductRequest;
import com.Steprella.Steprella.services.dtos.requests.products.UpdateProductRequest;
import com.Steprella.Steprella.services.dtos.responses.categories.ListCategoryResponse;
import com.Steprella.Steprella.services.dtos.responses.products.AddProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.UpdateProductResponse;
import com.Steprella.Steprella.services.mappers.ProductMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ShoeModelService shoeModelService;

    @Override
    public List<ListProductResponse> getAll() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::createProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ListProductResponse getById(int id) {
        Product product = productRepository.findById(id).orElse(null);

        return createProductResponse(product);
    }

    @Override
    public AddProductResponse add(AddProductRequest request) {
        Product addProduct = ProductMapper.INSTANCE.productFromAddRequest(request);
        Product saveProduct = productRepository.save(addProduct);
        return  ProductMapper.INSTANCE.addResponseFromProduct(saveProduct);
    }

    @Override
    public UpdateProductResponse update(UpdateProductRequest request) {
        Product updateProduct = ProductMapper.INSTANCE.productFromUpdateRequest(request);
        Product saveProduct = productRepository.save(updateProduct);
        return  ProductMapper.INSTANCE.updateResponseFromProduct(saveProduct);
    }

    @Override
    public void delete(int id) {
        Product product = productRepository.findById(id).orElse(null);
        assert product != null;
        productRepository.delete(product);
    }

    @Override
    public boolean isDistrictBelongsToCity(int modelId, int brandId) {
        return shoeModelService.getById(modelId).getBrandId() != brandId;
    }

    private ListProductResponse createProductResponse(Product product){
        ListCategoryResponse category = categoryService.getCategoryHierarchy(product.getCategory().getId());
        ListProductResponse response = ProductMapper.INSTANCE.listResponseFromProduct(product);
        response.setCategory(category);

        return response;
    }
}
