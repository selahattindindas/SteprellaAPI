package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.DistrictService;
import com.Steprella.Steprella.services.dtos.responses.district.ListDistrictResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DistrictServiceImpl implements DistrictService {

    @Override
    public List<ListDistrictResponse> getDistrictsByCityId(int cityId) {
        return List.of();
    }
}
