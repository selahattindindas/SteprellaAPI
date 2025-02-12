package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Favorite;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProductVariantMapper.class})
public interface FavoriteMapper {

    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    @Mapping(target = "productVariant", source = "productVariant")
    ListFavoriteResponse listResponseFromFavorite(Favorite favorite);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productVariant.id", source = "request.productVariantId")
    @Mapping(target = "customer", source = "customer")
    Favorite favoriteFromAddRequest(AddFavoriteRequest request, Customer customer);

    @Mapping(target = "productVariantId", source = "productVariant.id")
    AddFavoriteResponse addResponseFromFavorite(Favorite favorite);
}
