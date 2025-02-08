package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.products.AddProductRequest;
import com.Steprella.Steprella.services.dtos.requests.products.ProductSearchCriteria;
import com.Steprella.Steprella.services.dtos.requests.products.UpdateProductRequest;
import com.Steprella.Steprella.services.dtos.responses.products.AddProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.UpdateProductResponse;

import java.util.List;

public interface ProductService {

    List<ListProductResponse> getAll(int page, int size);

    List<ListProductResponse> getRandomVariants(int count);

    List<ListProductResponse> filter(ProductSearchCriteria criteria);

    List<ListProductResponse> search(String searchTerm);

    ListProductResponse getById(int id);

    AddProductResponse add(AddProductRequest request);

    UpdateProductResponse update(UpdateProductRequest request);

    void delete(int id);

    int getTotalCount();

    int getTotalCount(ProductSearchCriteria criteria);
}
