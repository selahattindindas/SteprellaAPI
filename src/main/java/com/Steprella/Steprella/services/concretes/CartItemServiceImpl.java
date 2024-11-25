package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.repositories.CartItemRepository;
import com.Steprella.Steprella.services.abstracts.CartItemService;
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

    @Override
    public List<ListCartItemResponse> getItemsByCartId(int cartId) {
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
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);

        return CartItemMapper.INSTANCE.listFromCartItem(cartItem);
    }

    @Override
    public AddCartItemResponse add(AddCartItemRequest request) {
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
        CartItem cartItem = cartItemRepository.findById(id).orElse(null);
        assert cartItem != null;
        cartItemRepository.delete(cartItem);
    }

    private void checkProductVariantAvailability(int productVariantId, int productVariantSizeId, int requestedQuantity) {

        ListProductVariantResponse productVariant = productVariantService.getById(productVariantId);

        ListProductSizeResponse productSize = productVariant.getProductSizes().stream()
                .filter(size -> size.getId() == productVariantSizeId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Geçersiz boyut numarası! Bu varyant için geçerli boyut değil."));

        int availableStock = productSize.getStockQuantity();
        if (availableStock < requestedQuantity) {
            throw new IllegalArgumentException("Yeterli stok bulunmamaktadır. Talep edilen miktar: " + requestedQuantity);
        }
    }

    private BigDecimal calculateTotalPrice(BigDecimal unitPrice, int quantity) {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
