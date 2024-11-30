package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.EntityValidator;
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
    private final EntityValidator entityValidator;

    @Override
    public List<ListCartItemResponse> getItemsByCartId(int cartId) {
        cartService.getById(cartId);

        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

        return cartItems.stream().map(cartItem -> {
            BigDecimal unitPrice = productVariantService.getUnitPriceByProductVariantId(cartItem.getProductVariant().getId());
            BigDecimal totalPrice = calculateTotalPrice(unitPrice, cartItem.getQuantity());

            cartItem.setUnitPrice(unitPrice);
            cartItem.setTotalPrice(totalPrice);
            return CartItemMapper.INSTANCE.listResponseFromCartItem(cartItem);
        }).collect(Collectors.toList());
    }

    @Override
    public ListCartItemResponse getById(int id) {
        CartItem cartItem = findCartItemById(id);
        return CartItemMapper.INSTANCE.listResponseFromCartItem(cartItem);
    }

    @Override
    public AddCartItemResponse add(AddCartItemRequest request) {
        cartService.getById(request.getCartId());
        productVariantService.getById(request.getProductVariantId());
        entityValidator.checkProductVariantAvailability(request.getProductVariantId(), request.getProductVariantSizeId(), request.getQuantity());

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
        entityValidator.checkProductVariantAvailability(request.getProductVariantId(), request.getProductVariantSizeId(), request.getQuantity());

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

    @Override
    public CartItem findByProductVariantIdAndCartId(int productVariantId, int cartId) {
        return cartItemRepository.findByProductVariantIdAndCartId(productVariantId, cartId);
    }

    public List<CartItem> getCartItemsForUser(int userId, List<Integer> cartItemIds) {
        return cartItemRepository.findAllById(cartItemIds)
                .stream()
                .filter(cartItem -> cartItem.getCart().getUser().getId() == userId)
                .collect(Collectors.toList());
    }

    public void deleteCartItemsForOrder(int userId, List<Integer> cartItemIds) {
        List<CartItem> cartItems = cartItemRepository.findAllById(cartItemIds);
        cartItems.stream()
                .filter(cartItem -> cartItem.getCart().getUser().getId() == userId)
                .forEach(cartItemRepository::delete);
    }

    @Override
    public List<Integer> validateCartItems(List<Integer> cartItemIds, int cartId) {
        return cartItemIds.stream()
                .filter(cartItemId -> cartItemRepository.findByIdAndCartId(cartItemId, cartId).isEmpty())
                .collect(Collectors.toList());
    }

    private BigDecimal calculateTotalPrice(BigDecimal unitPrice, int quantity) {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    private CartItem findCartItemById(int id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_CART_ITEM_NOT_FOUND));
    }
}
