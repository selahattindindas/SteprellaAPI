package com.Steprella.Steprella.core.utils;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Address;
import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.services.abstracts.*;
import com.Steprella.Steprella.services.dtos.requests.orderitems.AddOrderItemRequest;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityValidator {

    private final DistrictService districtService;
    private final ShoeModelService shoeModelService;
    private final ProductVariantService productVariantService;
    private final AddressService addressService;
    private final CartItemService cartItemService;

    @Autowired
    public EntityValidator(DistrictService districtService,
                           ShoeModelService shoeModelService,
                           AddressService addressService,
                           ProductVariantService productVariantService,
                           CartItemService cartItemService) {
        this.districtService = districtService;
        this.shoeModelService = shoeModelService;
        this.addressService = addressService;
        this.productVariantService = productVariantService;
        this.cartItemService = cartItemService;
    }

    public void validateShoeModelBrand(int modelId, int brandId) {
        int modelBrandId = shoeModelService.getById(modelId).getBrandId();

        if (modelBrandId != brandId)
            throw new BusinessException(Messages.Error.CUSTOM_BAD_REQUEST);

    }

    public void validateCityDistrictRelation(int cityId, int districtId) {
        int districtCityId = districtService.getById(districtId).getCityId();

        if (districtCityId != cityId)
            throw new BusinessException(Messages.Error.INVALID_DISTRICT_CITY_RELATION);

    }

    public void validateUserAddress(int userId, int shippingAddressId){
        Address address = addressService.getResponseById(shippingAddressId);

        int userAddressId = address.getUser().getId();

        if(userAddressId !=userId)
            throw new BusinessException(Messages.Error.ADDRESS_NOT_BELONG_TO_USER);
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

    public void validateProductAvailabilityForOrder(AddOrderItemRequest request, int cartId) {
        CartItem cartItem = cartItemService.findByProductVariantIdAndCartId(request.getProductVariantId(), cartId);
        if (cartItem == null || cartItem.getQuantity() < request.getQuantity()) {
            throw new BusinessException("Sepette yeterli ürün yok.");
        }
    }
}
