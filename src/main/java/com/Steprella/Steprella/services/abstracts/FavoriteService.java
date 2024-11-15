package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;

import java.util.List;

public interface FavoriteService {

    List<ListFavoriteResponse> getFavoritesByUserId(int userId);

    AddFavoriteResponse add(AddFavoriteRequest request);

    void delete(int id);
}
