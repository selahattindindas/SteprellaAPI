package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.ProductColorService;
import com.Steprella.Steprella.services.dtos.requests.productcolors.AddProductColorRequest;
import com.Steprella.Steprella.services.dtos.responses.productcolors.AddProductColorResponse;
import com.Steprella.Steprella.services.dtos.responses.productcolors.ListProductColorResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductColorServiceImpl implements ProductColorService {

    @Override
    public List<ListProductColorResponse> getProductColorsByProductId(int productId) {
        return List.of();
    }

    @Override
    public ListProductColorResponse getById(int id) {
        return null;
    }

    @Override
    public AddProductColorResponse add(AddProductColorRequest request) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
