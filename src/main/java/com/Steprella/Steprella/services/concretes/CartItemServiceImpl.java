package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.EntityValidator;
import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.entities.concretes.ProductSize;
import com.Steprella.Steprella.entities.concretes.ProductVariant;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.entities.concretes.Cart;
import com.Steprella.Steprella.repositories.CartItemRepository;
import com.Steprella.Steprella.services.abstracts.CartItemService;
import com.Steprella.Steprella.services.abstracts.CartService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.abstracts.CustomerService;
import com.Steprella.Steprella.services.dtos.requests.cartitems.AddCartItemRequest;
import com.Steprella.Steprella.services.dtos.requests.cartitems.UpdateCartItemRequest;
import com.Steprella.Steprella.services.dtos.responses.cart_items.AddCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.cart_items.ListCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.cart_items.UpdateCartItemResponse;
import com.Steprella.Steprella.services.mappers.CartItemMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductVariantService productVariantService;
    private final CartService cartService;
    private final EntityValidator entityValidator;
    private final CustomerService customerService;

    @Override
    public List<ListCartItemResponse> getItemsByCartId(int cartId, int page, int size) {
        cartService.getById(cartId);

        Pageable pageable = PageRequest.of(page, size);
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId, pageable).getContent();


        return cartItems.stream().map(cartItem -> {
            BigDecimal unitPrice = productVariantService.getUnitPriceByProductVariantId(cartItem.getProductVariant().getId());
            BigDecimal totalPrice = calculateTotalPrice(unitPrice, cartItem.getQuantity());

            cartItem.setUnitPrice(unitPrice);
            cartItem.setTotalPrice(totalPrice);

            boolean isInStock = checkStockAvailabilityForCartItem(cartItem);
            cartItem.setInStock(isInStock);

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
        Customer customer = customerService.getCustomerOfCurrentUser();
        Cart cart = customer.getCart();

        if (cart == null) {
            throw new BusinessException(Messages.Error.CUSTOM_CART_NOT_FOUND);
        }

        productVariantService.getById(request.getProductVariantId());
        entityValidator.checkProductVariantAvailability(request.getProductVariantId(), request.getProductVariantSizeId(), request.getQuantity());

        Optional<CartItem> existingCartItem = cartItemRepository.findByCartIdAndProductVariantIdAndProductSizeId(
                cart.getId(),
                request.getProductVariantId(),
                request.getProductVariantSizeId()
        );

        if (existingCartItem.isPresent()) {
            throw new BusinessException(Messages.Error.CUSTOM_PRODUCT_ALREADY_IN_CART);
        }

        BigDecimal unitPrice = productVariantService.getUnitPriceByProductVariantId(request.getProductVariantId());
        BigDecimal totalPrice = calculateTotalPrice(unitPrice, request.getQuantity());

        CartItem addCartItem = CartItemMapper.INSTANCE.cartItemFromAddRequest(request);
        addCartItem.setUnitPrice(unitPrice);
        addCartItem.setTotalPrice(totalPrice);
        addCartItem.setCart(cart);

        CartItem savedCartItem = cartItemRepository.save(addCartItem);

        return CartItemMapper.INSTANCE.addResponseFromCartItem(savedCartItem);
    }

    @Override
    public UpdateCartItemResponse update(UpdateCartItemRequest request) {
        CartItem existingCartItem = findCartItemById(request.getId());
        Customer customer = customerService.getCustomerOfCurrentUser();
        Cart cart = customer.getCart();

        if (cart == null) {
            throw new BusinessException(Messages.Error.CUSTOM_CART_NOT_FOUND);
        }

        productVariantService.getById(request.getProductVariantId());
        entityValidator.checkProductVariantAvailability(request.getProductVariantId(), request.getProductVariantSizeId(), request.getQuantity());

        BigDecimal unitPrice = productVariantService.getUnitPriceByProductVariantId(request.getProductVariantId());
        BigDecimal totalPrice = calculateTotalPrice(unitPrice, request.getQuantity());


        CartItem updateCartItem = CartItemMapper.INSTANCE.cartItemFromUpdateRequest(request);
        updateCartItem.setUnitPrice(unitPrice);
        updateCartItem.setTotalPrice(totalPrice);
        updateCartItem.setCart(cart); 

        CartItem savedCartItem = cartItemRepository.save(updateCartItem);

        return CartItemMapper.INSTANCE.updateResponseFromCartItem(savedCartItem);
    }

    @Override
    public void delete(int id) {
        CartItem cartItem = findCartItemById(id);
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void deleteCartItemsForOrder(List<Integer> cartItemIds) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        List<CartItem> cartItems = cartItemRepository.findAllById(cartItemIds);
        cartItems.stream()
                .filter(cartItem -> cartItem.getCart().getCustomer().equals(customer))
                .forEach(cartItemRepository::delete);
    }

    @Override
    public List<CartItem> getCartItemsForOrder(List<Integer> cartItemIds) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        return cartItemRepository.findAllById(cartItemIds)
                .stream()
                .filter(cartItem -> cartItem.getCart().getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    @Override
    public boolean validateCartItems(List<Integer> cartItemIds) {
        List<CartItem> customerCartItems = getCartItemsForOrder(cartItemIds);
        return customerCartItems.size() == cartItemIds.size();
    }

    @Override
    public int getTotalCount() {
        return (int) cartItemRepository.count();
    }

    @Override
    public CartItem findByProductVariantIdAndCartId(int productVariantId, int cartId) {
        return cartItemRepository.findByProductVariantIdAndCartId(productVariantId, cartId);
    }

    public boolean checkStockAvailabilityForCartItem(CartItem cartItem) {
        ProductVariant productVariant = cartItem.getProductVariant();

        ProductSize size = productVariant.getProductSizes()
                .stream()
                .filter(s -> s.getId() == cartItem.getProductSize().getId())
                .findFirst()
                .orElse(null);

        return size != null && size.getStockQuantity() >= cartItem.getQuantity();
    }

    private CartItem findCartItemById(int id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_CART_ITEM_NOT_FOUND));
    }

    private BigDecimal calculateTotalPrice(BigDecimal unitPrice, int quantity) {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
