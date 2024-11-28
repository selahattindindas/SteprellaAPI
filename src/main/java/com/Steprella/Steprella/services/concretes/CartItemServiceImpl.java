package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.repositories.CartItemRepository;
import com.Steprella.Steprella.services.abstracts.CartItemService;
import com.Steprella.Steprella.services.abstracts.CartService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.dtos.requests.cartitems.AddCartItemRequest;
import com.Steprella.Steprella.services.dtos.requests.cartitems.UpdateCartItemRequest;
import com.Steprella.Steprella.services.dtos.responses.cart_items.AddCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.cart_items.ListCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.cart_items.UpdateCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.productsizes.ListProductSizeResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import com.Steprella.Steprella.services.mappers.CartItemMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductVariantService productVariantService;
    private final CartService cartService;

    @Override
    public List<ListCartItemResponse> getItemsByCartId(int cartId) {
        cartService.getById(cartId);

        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

        return cartItems.stream().map(cartItem -> {
            BigDecimal unitPrice = productVariantService.getUnitPriceByProductVariantId(cartItem.getProductVariant().getId());
            BigDecimal totalPrice = calculateTotalPrice(unitPrice, cartItem.getQuantity());

            cartItem.setUnitPrice(unitPrice);
            cartItem.setTotalPrice(totalPrice);
            return CartItemMapper.INSTANCE.listFromCartItem(cartItem);
        }).collect(Collectors.toList());
    }

    @Override
    public ListCartItemResponse getById(int id) {
        CartItem cartItem = findCartItemById(id);
        return CartItemMapper.INSTANCE.listFromCartItem(cartItem);
    }

    @Override
    public AddCartItemResponse add(AddCartItemRequest request) {
        cartService.getById(request.getCartId());
        productVariantService.getById(request.getProductVariantId());
        checkProductVariantAvailability(request.getProductVariantId(), request.getProductVariantSizeId(), request.getQuantity());

        BigDecimal unitPrice = productVariantService.getUnitPriceByProductVariantId(request.getProductVariantId());
        BigDecimal totalPrice = calculateTotalPrice(unitPrice, request.getQuantity());

        CartItem addCartItem = CartItemMapper.INSTANCE.cartItemFromAddRequest(request);
        addCartItem.setUnitPrice(unitPrice);
        addCartItem.setTotalPrice(totalPrice);

        CartItem savedCartItem = cartItemRepository.save(addCartItem);

        return CartItemMapper.INSTANCE.addResponseFromCartItem(savedCartItem);
    }

    @Override
    public UpdateCartItemResponse update(UpdateCartItemRequest request) {
        findCartItemById(request.getId());
        cartService.getById(request.getCartId());
        productVariantService.getById(request.getProductVariantId());
        checkProductVariantAvailability(request.getProductVariantId(), request.getProductVariantSizeId(), request.getQuantity());

        BigDecimal unitPrice = productVariantService.getUnitPriceByProductVariantId(request.getProductVariantId());
        BigDecimal totalPrice = calculateTotalPrice(unitPrice, request.getQuantity());

        CartItem updateCartItem = CartItemMapper.INSTANCE.cartItemFromUpdateRequest(request);
        updateCartItem.setUnitPrice(unitPrice);
        updateCartItem.setTotalPrice(totalPrice);

        CartItem savedCartItem = cartItemRepository.save(updateCartItem);

        return CartItemMapper.INSTANCE.updateResponseFromCartItem(savedCartItem);
    }

    @Override
    public void delete(int id) {
        CartItem cartItem = findCartItemById(id);
        cartItemRepository.delete(cartItem);
    }

    private void checkProductVariantAvailability(int productVariantId, int productVariantSizeId, int requestedQuantity) {

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

    private BigDecimal calculateTotalPrice(BigDecimal unitPrice, int quantity) {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    private CartItem findCartItemById(int id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_CART_ITEM_NOT_FOUND));
    }
}
