package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.CalculationUtils;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private UserService userService;

    @Override
    @Cacheable(value="carts", key="#userId")
    public ListCartResponse getCartByUserId(int userId) {
        userService.getById(userId);
        Cart cart = cartRepository.findByUserId(userId);

        List<CartItem> cartItems = cart.getCartItems();

        int totalItems = CalculationUtils.calculateTotalItems(cartItems, CartItem::getQuantity);
        BigDecimal totalPrice = CalculationUtils.calculateTotalPrice(
                cartItems,
                cartItem -> cartItem.getCart().getId(),
                CartItem::getTotalPrice,
                cart.getId()
        );

        ListCartResponse response = CartMapper.INSTANCE.listResponseFromCart(cart);
        response.setTotalPrice(totalPrice);
        response.setTotalItems(totalItems);

        return response;
    }

    @Override
    @Cacheable(value = "carts", key = "#id")
    public ListCartResponse getById(int id) {
        Cart cart = findCartById(id);
        return CartMapper.INSTANCE.listResponseFromCart(cart);
    }

    @Override
    @CachePut(value = "carts", key = "#result.id")
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
    @CacheEvict(value = "carts", allEntries = true)
    public void delete(int id) {
        Cart cart = findCartById(id);
        cartRepository.delete(cart);
    }

    private Cart findCartById(int id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_CART_NOT_FOUND));
    }
}
