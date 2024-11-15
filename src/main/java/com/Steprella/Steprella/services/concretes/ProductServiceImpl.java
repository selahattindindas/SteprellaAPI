package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.ProductService;
import com.Steprella.Steprella.services.dtos.requests.products.AddProductRequest;
import com.Steprella.Steprella.services.dtos.requests.products.UpdateProductRequest;
import com.Steprella.Steprella.services.dtos.responses.products.AddProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import com.Steprella.Steprella.services.dtos.responses.products.UpdateProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Override
    public List<ListProductResponse> getAll() {
        return List.of();
    }

    @Override
    public ListProductResponse getById(int id) {
        return null;
    }

    @Override
    public AddProductResponse add(AddProductRequest request) {
        return null;
    }

    @Override
    public UpdateProductResponse update(UpdateProductRequest request) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
