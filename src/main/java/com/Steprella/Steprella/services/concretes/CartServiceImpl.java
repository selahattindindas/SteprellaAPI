package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Cart;
import com.Steprella.Steprella.entities.concretes.CartItem;
import com.Steprella.Steprella.repositories.CartRepository;
import com.Steprella.Steprella.services.abstracts.CartService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.carts.AddCartRequest;
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
    private UserService userService;

    @Override
    public ListCartResponse getCartByUserId(int userId) {
        userService.getById(userId);
        Cart cart = cartRepository.findByUserId(userId);

        List<CartItem> cartItems = cart.getCartItems();

        int totalItems = cartItems.stream().mapToInt(CartItem::getQuantity).sum();
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        ListCartResponse response = CartMapper.INSTANCE.listResponseFromCart(cart);
        response.setCartItems(CartMapper.INSTANCE.mapCartItemsToDTO(cart.getCartItems()));
        response.setTotalPrice(totalPrice);
        response.setTotalItems(totalItems);

        return response;
    }
    @Override
    public ListCartResponse getById(int id) {
        Cart cart = findCartById(id);
        return CartMapper.INSTANCE.listResponseFromCart(cart);
    }

    @Override
    public AddCartResponse add(AddCartRequest request) {
        userService.getResponseById(request.getUserId());

        if (cartRepository.existsByUserId(request.getUserId())) {
            throw new BusinessException(Messages.Error.CART_ALREADY_EXISTS);
        }

        Cart addCart = CartMapper.INSTANCE.cartFromAddRequest(request);
        Cart savedCart = cartRepository.save(addCart);

        return CartMapper.INSTANCE.addResponseFrom(savedCart);
    }

    @Override
    public void delete(int id) {
        Cart cart = findCartById(id);
        cartRepository.delete(cart);
    }

    private Cart findCartById(int id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_CART_NOT_FOUND));
    }
}
