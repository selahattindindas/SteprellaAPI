package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.entities.concretes.Favorite;
import com.Steprella.Steprella.repositories.FavoriteRepository;
import com.Steprella.Steprella.services.abstracts.FavoriteService;
import com.Steprella.Steprella.services.abstracts.CustomerService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import com.Steprella.Steprella.services.mappers.FavoriteMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ProductVariantService productVariantService;
    private final CustomerService customerService;

    @Override
    public List<ListFavoriteResponse> getFavorites(int page, int size) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        
        Pageable pageable = PageRequest.of(page, size);
        List<Favorite> favorites = favoriteRepository.findByCustomerId(customer.getId(), pageable).getContent();

        return favorites.stream()
                .map(FavoriteMapper.INSTANCE::listResponseFromFavorite)
                .collect(Collectors.toList());
    }
    
    @Override
    public AddFavoriteResponse add(AddFavoriteRequest request) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        
        if (favoriteRepository.existsByCustomerIdAndProductVariantId(
                customer.getId(), request.getProductVariantId())) {
            throw new BusinessException(Messages.Error.FAVORITE_ALREADY_EXISTS);
        }

        Favorite favorite = FavoriteMapper.INSTANCE.favoriteFromAddRequest(request, customer);

        Favorite savedFavorite = favoriteRepository.save(favorite);
        return FavoriteMapper.INSTANCE.addResponseFromFavorite(savedFavorite);
    }

    @Override
    public void delete(int id) {
        Favorite favorite = findFavoriteAndValidateOwnership(id);
        favoriteRepository.delete(favorite);
    }

    @Override
    public int getTotalCount() {
        Customer customer = customerService.getCustomerOfCurrentUser();
        return favoriteRepository.findByCustomerId(customer.getId()).size();
    }

    private Favorite findFavoriteAndValidateOwnership(int id) {
        Customer customer = customerService.getCustomerOfCurrentUser();
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_FAVORITE_NOT_FOUND));

        if (favorite.getCustomer().getId() != customer.getId()) {
            throw new BusinessException(Messages.Error.CUSTOM_FAVORITE_ACCESS_DENIED);
        }

        return favorite;
    }
}
