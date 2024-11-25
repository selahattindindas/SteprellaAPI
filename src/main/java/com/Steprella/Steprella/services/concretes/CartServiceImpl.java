package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.Cart;
import com.Steprella.Steprella.repositories.CartRepository;
import com.Steprella.Steprella.services.abstracts.CartItemService;
import com.Steprella.Steprella.services.abstracts.CartService;
import com.Steprella.Steprella.services.dtos.requests.carts.AddCartRequest;
import com.Steprella.Steprella.services.dtos.responses.cart_items.ListCartItemResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.AddCartResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.ListCartResponse;
import com.Steprella.Steprella.services.mappers.CartMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemService cartItemService;

    @Override
    public ListCartResponse getCartByUserId(int userId) {
        Cart cart = cartRepository.findByUserId(userId);

        List<ListCartItemResponse> cartItems = cartItemService.getItemsByCartId(cart.getId());

        int totalItems = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (ListCartItemResponse cartItem : cartItems) {
            totalItems += cartItem.getQuantity();
            totalPrice = totalPrice.add(cartItem.getTotalPrice());
        }

        ListCartResponse response = CartMapper.INSTANCE.listResponseFromCart(cart);
        response.setCartItems(cartItems);
        response.setTotalPrice(totalPrice);
        response.setTotalItems(totalItems);

        return response;
    }
    @Override
    public ListCartResponse getById(int id) {
        Cart cart = cartRepository.findById(id).orElse(null);

        return CartMapper.INSTANCE.listResponseFromCart(cart);
    }

    @Override
    public AddCartResponse add(AddCartRequest request) {
        Cart addCart = CartMapper.INSTANCE.cartFromAddRequest(request);
        Cart savedCart = cartRepository.save(addCart);

        return CartMapper.INSTANCE.addResponseFrom(savedCart);
    }

    @Override
    public void delete(int id) {
        Cart cart = cartRepository.findById(id).orElse(null);
        assert cart != null;
        cartRepository.delete(cart);
    }
}
