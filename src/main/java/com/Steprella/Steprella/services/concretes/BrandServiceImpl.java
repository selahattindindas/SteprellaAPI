package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.BrandService;
import com.Steprella.Steprella.services.dtos.requests.brands.AddBrandRequest;
import com.Steprella.Steprella.services.dtos.requests.brands.UpdateBrandRequest;
import com.Steprella.Steprella.services.dtos.responses.brands.AddBrandResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.ListBrandResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.UpdateBrandResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {

    @Override
    public List<ListBrandResponse> getAll() {
        return List.of();
    }

    @Override
    public ListBrandResponse getById(int id) {
        return null;
    }

    @Override
    public AddBrandResponse add(AddBrandRequest request) {
        return null;
    }

    @Override
    public UpdateBrandResponse update(UpdateBrandRequest request) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
