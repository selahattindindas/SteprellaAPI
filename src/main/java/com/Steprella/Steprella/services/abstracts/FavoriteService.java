package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;

import java.util.List;

public interface FavoriteService {

    List<ListFavoriteResponse> getFavorites(int page, int size);

    AddFavoriteResponse add(AddFavoriteRequest request);

    void delete(int id);

    int getTotalCount();

    List<Integer> getCurrentUserFavoriteProductVariantIds();
}
