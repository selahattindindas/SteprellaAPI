package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.RatingUtils;
import com.Steprella.Steprella.core.utils.exceptions.types.BusinessException;
import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.Comment;
import com.Steprella.Steprella.entities.concretes.Favorite;
import com.Steprella.Steprella.repositories.FavoriteRepository;
import com.Steprella.Steprella.services.abstracts.FavoriteService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.abstracts.UserService;
import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import com.Steprella.Steprella.services.mappers.FavoriteMapper;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    private final UserService userService;

    @Override
    @Cacheable(value="favorites", key="#userId")
    public List<ListFavoriteResponse> getFavoritesByUserId(int userId, int page, int size) {
        userService.getResponseById(userId);

        Pageable pageable = PageRequest.of(page, size);
        List<Favorite> favorites = favoriteRepository.findByUserId(userId, pageable).getContent();

        return favorites.stream()
                .map(favorite -> {
                    List<Comment> comments = favorite.getProductVariant().getComments();
                    double averageRating = RatingUtils.calculateAverageRating(comments);
                    int totalComments = RatingUtils.calculateTotalComments(comments);

                    ListFavoriteResponse response = FavoriteMapper.INSTANCE.listResponseFromFavorite(favorite);
                    response.getProductVariant().setRating(averageRating);
                    response.getProductVariant().setRatingCount(totalComments);

                    return response;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    @CachePut(value = "favorites", key = "#result.id")
    public AddFavoriteResponse add(AddFavoriteRequest request) {
        validateFavoriteDependencies(request.getProductVariantId(), request.getUserId());

        if (favoriteRepository.existsByUserIdAndProductVariantId(request.getUserId(), request.getProductVariantId())) {
            throw new BusinessException(Messages.Error.FAVORITE_ALREADY_EXISTS);
        }

        Favorite addFavorite = FavoriteMapper.INSTANCE.favoriteFromAddRequest(request);
        Favorite savedFavorite = favoriteRepository.save(addFavorite);

        return FavoriteMapper.INSTANCE.addResponseFromFavorite(savedFavorite);
    }

    @Override
    @CacheEvict(value = "favorites", key = "#id")
    public void delete(int id) {
        Favorite favorite = favoriteRepository.findById(id).
                orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_PRODUCT_NOT_FOUND));
        favoriteRepository.delete(favorite);
    }

    @Override
    public int getTotalCount() {
        return (int) favoriteRepository.count();
    }

    private void validateFavoriteDependencies(int productVariantId, int userId) {
        productVariantService.getById(productVariantId);
        userService.getResponseById(userId);
    }
}
