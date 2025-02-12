package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Cart;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.repositories.CartRepository;
import com.Steprella.Steprella.services.abstracts.CartService;
import com.Steprella.Steprella.services.abstracts.CustomerService;
import com.Steprella.Steprella.services.dtos.responses.carts.AddCartResponse;
import com.Steprella.Steprella.services.dtos.responses.carts.ListCartResponse;
import com.Steprella.Steprella.services.mappers.CartMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CustomerService customerService;

    @Override
    public ListCartResponse getCart() {
        Customer customer = customerService.getCustomerOfCurrentUser();
        Cart cart = findCartByCustomerId(customer.getId());
        return CartMapper.INSTANCE.listResponseFromCart(cart);
    }

    @Override
    public AddCartResponse createCart(Customer customer) {
        if (cartRepository.existsByCustomerId(customer.getId())) {
            throw new BusinessException(Messages.Error.CART_ALREADY_EXISTS);
        }

        Cart cart = CartMapper.INSTANCE.createCart(customer);
        cart.setCartItems(new ArrayList<>());
        cart.setTotalItems(0);
        cart.setTotalPrice(BigDecimal.ZERO);

        Cart savedCart = cartRepository.save(cart);
        return CartMapper.INSTANCE.addResponseFromCart(savedCart);
    }

    private Cart findCartByCustomerId(int customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_CART_NOT_FOUND));
    }

    @Override
    public Cart getById(int id) {
        return cartRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_CART_NOT_FOUND));
    }
}
