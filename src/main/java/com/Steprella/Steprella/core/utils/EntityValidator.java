package com.Steprella.Steprella.core.utils;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Address;
import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.services.abstracts.*;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class EntityValidator {

    private final DistrictService districtService;
    private final ShoeModelService shoeModelService;
    private final ProductVariantService productVariantService;
    private final AddressService addressService;
    private final CustomerService customerService;

    @Autowired
    public EntityValidator(@Lazy DistrictService districtService,
                           @Lazy ShoeModelService shoeModelService,
                           @Lazy AddressService addressService,
                           @Lazy ProductVariantService productVariantService,
                           @Lazy CartItemService cartItemService,
                           @Lazy CustomerService customerService) {
        this.districtService = districtService;
        this.shoeModelService = shoeModelService;
        this.addressService = addressService;
        this.productVariantService = productVariantService;
        this.customerService = customerService;
    }

    public void validateShoeModelBrand(int modelId, int brandId) {
        int modelBrandId = shoeModelService.getById(modelId).getBrandId();

        if (modelBrandId != brandId)
            throw new BusinessException(Messages.Error.CUSTOM_BAD_REQUEST);
    }

    public void validateCityDistrictRelation(int districtId, int cityId) {
        int districtCityId = districtService.getById(districtId).getCityId();

        if (districtCityId != cityId)
            throw new BusinessException(Messages.Error.INVALID_DISTRICT_CITY_RELATION);
    }

    public void validateCustomerAddress(int addressId) {
        Address address = addressService.getAddressById(addressId);
        int currentCustomerId = customerService.getCustomerOfCurrentUser().getId();

        if (address.getCustomer().getId() != currentCustomerId) {
            throw new BusinessException(Messages.Error.CUSTOM_ADDRESS_ACCESS_DENIED);
        }
    }

    public void checkProductVariantAvailability(int productVariantId, int productVariantSizeId, int requestedQuantity) {
        ListProductVariantResponse productVariant = productVariantService.getById(productVariantId);

        ListProductSizeResponse productSize = productVariant.getProductSizes().stream()
                .filter(size -> size.getId() == productVariantSizeId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Messages.Error.INVALID_SIZE_FOR_VARIANT));

        int availableStock = productSize.getStockQuantity();
        if (availableStock < requestedQuantity) {
            throw new BusinessException(String.format(Messages.Error.INSUFFICIENT_STOCK, requestedQuantity));
        }
    }

    public void validateProductAvailabilityForOrder(CartItem cartItem) {
        ListProductVariantResponse productVariant = productVariantService.getById(cartItem.getProductVariant().getId());

        ListProductSizeResponse productSize = productVariant.getProductSizes().stream()
                .filter(size -> size.getId() == cartItem.getProductSize().getId())
                .findFirst()
                .orElseThrow(() -> new NotFoundException(Messages.Error.INVALID_SIZE_FOR_VARIANT));

        int availableStock = productSize.getStockQuantity();
        if (availableStock < cartItem.getQuantity()) {
            throw new BusinessException(String.format(Messages.Error.INSUFFICIENT_STOCK, cartItem.getQuantity()));
        }
    }
}