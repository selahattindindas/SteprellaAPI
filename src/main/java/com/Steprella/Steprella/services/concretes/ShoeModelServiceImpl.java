package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.ShoeModelService;
import com.Steprella.Steprella.services.dtos.requests.shoemodels.AddShoeModelRequest;
import com.Steprella.Steprella.services.dtos.requests.shoemodels.UpdateShoeModelRequest;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.AddShoeModelResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.ListShoeModelResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.UpdateShoeModelResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShoeModelServiceImpl implements ShoeModelService {

    @Override
    public List<ListShoeModelResponse> getShoeModelsByBrandId(int brandId) {
        return List.of();
    }

    @Override
    public ListShoeModelResponse getById(int id) {
        return null;
    }

    @Override
    public AddShoeModelResponse add(AddShoeModelRequest request) {
        return null;
    }

    @Override
    public UpdateShoeModelResponse update(UpdateShoeModelRequest request) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
