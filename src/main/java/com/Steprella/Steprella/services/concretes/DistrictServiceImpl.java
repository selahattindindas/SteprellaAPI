package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.District;
import com.Steprella.Steprella.repositories.DistrictRepository;
import com.Steprella.Steprella.services.abstracts.CityService;
import com.Steprella.Steprella.services.abstracts.DistrictService;
import com.Steprella.Steprella.services.dtos.responses.district.ListDistrictResponse;
import com.Steprella.Steprella.services.mappers.DistrictMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    private final DistrictRepository districtRepository;
    private CityService cityService;

    @Override
    public List<ListDistrictResponse> getDistrictsByCityId(int cityId) {
        cityService.getById(cityId);

        List<District> districts = districtRepository.findByCityId(cityId);
        return districts.stream().map(DistrictMapper.INSTANCE::listResponseFromDistrict).collect(Collectors.toList());
    }

    @Override
    public ListDistrictResponse getById(int id) {
        District district = districtRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_DISTRICT_NOT_FOUND));

        return DistrictMapper.INSTANCE.listResponseFromDistrict(district);
    }
}
