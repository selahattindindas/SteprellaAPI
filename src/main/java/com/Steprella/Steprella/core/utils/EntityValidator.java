package com.Steprella.Steprella.core.utils;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.services.abstracts.DistrictService;
import com.Steprella.Steprella.services.abstracts.ShoeModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityValidator {

    private final DistrictService districtService;
    private final ShoeModelService shoeModelService;

    @Autowired
    public EntityValidator(DistrictService districtService, ShoeModelService shoeModelService) {
        this.districtService = districtService;
        this.shoeModelService = shoeModelService;
    }

    public void validateShoeModelBrand(int modelId, int brandId) {
        int modelBrandId = shoeModelService.getById(modelId).getBrandId();
        if (modelBrandId != brandId) {
            throw new BusinessException(Messages.Error.CUSTOM_BAD_REQUEST);
        }
    }

    public void validateCityDistrictRelation(int cityId, int districtId) {
        int districtCityId = districtService.getById(districtId).getCityId();
        if (districtCityId != cityId) {
            throw new BusinessException(Messages.Error.INVALID_DISTRICT_CITY_RELATION);
        }
    }
}
