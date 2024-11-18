package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.ShoeModel;
import com.Steprella.Steprella.repositories.ShoeModelRepository;
import com.Steprella.Steprella.services.abstracts.ShoeModelService;
import com.Steprella.Steprella.services.dtos.requests.shoemodels.AddShoeModelRequest;
import com.Steprella.Steprella.services.dtos.requests.shoemodels.UpdateShoeModelRequest;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.AddShoeModelResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.ListShoeModelResponse;
import com.Steprella.Steprella.services.dtos.responses.shoemodels.UpdateShoeModelResponse;
import com.Steprella.Steprella.services.mappers.ShoeModelMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShoeModelServiceImpl implements ShoeModelService {

    private ShoeModelRepository shoeModelRepository;

    @Override
    public List<ListShoeModelResponse> getAll() {
        List<ShoeModel> shoeModels = shoeModelRepository.findAll();
        return shoeModels.stream().map(ShoeModelMapper.INSTANCE::listFromShoeModel).collect(Collectors.toList());
    }

    @Override
    public List<ListShoeModelResponse> getShoeModelsByBrandId(int brandId) {
        List<ShoeModel> shoeModels = shoeModelRepository.findShoeModelByBrandId(brandId);

        return shoeModels.stream().map(ShoeModelMapper.INSTANCE::listFromShoeModel).collect(Collectors.toList());
    }

    @Override
    public ListShoeModelResponse getById(int id) {
        ShoeModel shoeModel = shoeModelRepository.findById(id).orElse(null);

        return ShoeModelMapper.INSTANCE.listFromShoeModel(shoeModel);
    }

    @Override
    public AddShoeModelResponse add(AddShoeModelRequest request) {
        ShoeModel addModel = ShoeModelMapper.INSTANCE.shoeModelFromAddRequest(request);
        ShoeModel saveModel = shoeModelRepository.save(addModel);
        return ShoeModelMapper.INSTANCE.addResponseFromShoeModel(saveModel);
    }

    @Override
    public UpdateShoeModelResponse update(UpdateShoeModelRequest request) {
        ShoeModel updateModel = ShoeModelMapper.INSTANCE.shoeModelFromUpdateRequest(request);
        ShoeModel saveModel = shoeModelRepository.save(updateModel);
        return ShoeModelMapper.INSTANCE.updateResponseFromShoeModel(saveModel);
    }

    @Override
    public void delete(int id) {
        ShoeModel model = shoeModelRepository.findById(id).orElse(null);
        assert model != null;
        shoeModelRepository.delete(model);
    }
}
