package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Brand;
import com.Steprella.Steprella.repositories.BrandRepository;
import com.Steprella.Steprella.services.abstracts.BrandService;
import com.Steprella.Steprella.services.dtos.requests.brands.AddBrandRequest;
import com.Steprella.Steprella.services.dtos.requests.brands.UpdateBrandRequest;
import com.Steprella.Steprella.services.dtos.responses.brands.AddBrandResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.ListBrandResponse;
import com.Steprella.Steprella.services.dtos.responses.brands.UpdateBrandResponse;
import com.Steprella.Steprella.services.mappers.BrandMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public List<ListBrandResponse> getAll() {
        List<Brand> brands = brandRepository.findAll();

        return brands.stream().map(BrandMapper.INSTANCE::listResponseFromBrand).collect(Collectors.toList());
    }

    @Override
    public ListBrandResponse getById(int id) {
        Brand brand = findBrandById(id);
        return BrandMapper.INSTANCE.listResponseFromBrand(brand);
    }

    @Override
    public AddBrandResponse add(AddBrandRequest request) {
        Brand newBrand = BrandMapper.INSTANCE.brandFromAddRequest(request);
        Brand savedBrand = brandRepository.save(newBrand);

        return BrandMapper.INSTANCE.addResponseBrand(savedBrand);
    }

    @Override
    public UpdateBrandResponse update(UpdateBrandRequest request) {
        findBrandById(request.getId());

        Brand updateBrand = BrandMapper.INSTANCE.brandFromUpdateRequest(request);
        Brand savedBrand = brandRepository.save(updateBrand);

        return BrandMapper.INSTANCE.updateResponseBrand(savedBrand);
    }

    @Override
    public void delete(int id) {
        Brand brand = findBrandById(id);
        brandRepository.delete(brand);
    }

    private Brand findBrandById(int id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_BRAND_NOT_FOUND));
    }
}
