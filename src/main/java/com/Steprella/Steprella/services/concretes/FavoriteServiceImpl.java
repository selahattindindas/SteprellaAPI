package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Favorite;
import com.Steprella.Steprella.repositories.FavoriteRepository;
import com.Steprella.Steprella.services.abstracts.FavoriteService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import com.Steprella.Steprella.services.mappers.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ProductVariantService productVariantService;
    private final UserService userService;

    @Autowired
    public FavoriteServiceImpl(@Lazy ProductVariantService productVariantService,
                               @Lazy UserService userService,
                               FavoriteRepository favoriteRepository){
        this.productVariantService = productVariantService;
        this.userService = userService;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public List<ListFavoriteResponse> getFavoritesByUserId(int userId) {
        userService.getResponseById(userId);
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);

        return favorites.stream().map(FavoriteMapper.INSTANCE::listResponseFromFavorite)
                .collect(Collectors.toList());
    }

    @Override
    public AddFavoriteResponse add(AddFavoriteRequest request) {
        validateFavoriteDependencies(request.getProductVariantId(), request.getUserId());

        Favorite addFavorite = FavoriteMapper.INSTANCE.favoriteFromAddRequest(request);
        Favorite savedFavorite = favoriteRepository.save(addFavorite);

        return FavoriteMapper.INSTANCE.addResponseFromFavorite(savedFavorite);
    }

    @Override
    public void delete(int id) {
        Favorite favorite = favoriteRepository.findById(id).
                orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_PRODUCT_NOT_FOUND));
        favoriteRepository.delete(favorite);
    }

    private void validateFavoriteDependencies(int productVariantId, int userId) {
        productVariantService.getById(productVariantId);
        userService.getResponseById(userId);
    }
}
