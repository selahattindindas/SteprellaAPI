package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.ShoeModel;
import com.Steprella.Steprella.repositories.ShoeModelRepository;
import com.Steprella.Steprella.services.abstracts.BrandService;
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

    private final ShoeModelRepository shoeModelRepository;
    private final BrandService brandService;

    @Override
    public List<ListShoeModelResponse> getAll() {
        List<ShoeModel> shoeModels = shoeModelRepository.findAll();
        return shoeModels.stream().map(ShoeModelMapper.INSTANCE::listFromShoeModel).collect(Collectors.toList());
    }

    @Override
    public List<ListShoeModelResponse> getShoeModelsByBrandId(int brandId) {
        brandService.getById(brandId);

        List<ShoeModel> shoeModels = shoeModelRepository.findShoeModelByBrandId(brandId);
        return shoeModels.stream().map(ShoeModelMapper.INSTANCE::listFromShoeModel).collect(Collectors.toList());
    }

    @Override
    public ListShoeModelResponse getById(int id) {
        ShoeModel model = findShoeModelById(id);
        return ShoeModelMapper.INSTANCE.listFromShoeModel(model);
    }

    @Override
    public AddShoeModelResponse add(AddShoeModelRequest request) {
        brandService.getById(request.getBrandId());

        ShoeModel addModel = ShoeModelMapper.INSTANCE.shoeModelFromAddRequest(request);
        ShoeModel savedModel = shoeModelRepository.save(addModel);

        return ShoeModelMapper.INSTANCE.addResponseFromShoeModel(savedModel);
    }

    @Override
    public UpdateShoeModelResponse update(UpdateShoeModelRequest request) {
        findShoeModelById(request.getId());
        brandService.getById(request.getBrandId());

        ShoeModel updateModel = ShoeModelMapper.INSTANCE.shoeModelFromUpdateRequest(request);
        ShoeModel savedModel = shoeModelRepository.save(updateModel);

        return ShoeModelMapper.INSTANCE.updateResponseFromShoeModel(savedModel);
    }

    @Override
    public void delete(int id) {
        ShoeModel model = findShoeModelById(id);
        shoeModelRepository.delete(model);
    }

    private ShoeModel findShoeModelById(int id) {
        return shoeModelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_SHOE_MODEL_NOT_FOUND));
    }
}
