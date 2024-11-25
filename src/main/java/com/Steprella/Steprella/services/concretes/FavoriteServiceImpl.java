package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.Favorite;
import com.Steprella.Steprella.repositories.FavoriteRepository;
import com.Steprella.Steprella.services.abstracts.FavoriteService;
import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import com.Steprella.Steprella.services.mappers.FavoriteMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Override
    public List<ListFavoriteResponse> getFavoritesByUserId(int userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);

        return favorites.stream().map(FavoriteMapper.INSTANCE::listResponseFromFavorite)
                .collect(Collectors.toList());
    }

    @Override
    public AddFavoriteResponse add(AddFavoriteRequest request) {
        Favorite addFavorite = FavoriteMapper.INSTANCE.favoriteFromAddRequest(request);
        Favorite savedFavorite = favoriteRepository.save(addFavorite);

        return FavoriteMapper.INSTANCE.addResponseFromFavorite(savedFavorite);
    }

    @Override
    public void delete(int id) {
        Favorite favorite = favoriteRepository.findById(id).orElse(null);
        assert favorite != null;
        favoriteRepository.delete(favorite);
    }
}
