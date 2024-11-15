package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.FavoriteService;
import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    @Override
    public List<ListFavoriteResponse> getFavoritesByUserId(int userId) {
        return List.of();
    }

    @Override
    public AddFavoriteResponse add(AddFavoriteRequest request) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
