package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Favorite;
import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ProductVariantMapper.class)
public interface FavoriteMapper {

    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    @Mapping(target = "productVariant", expression = "java(ProductVariantMapper.INSTANCE.listResponseFromProductVariant(favorite.getProductVariant()))")
    ListFavoriteResponse listResponseFromFavorite(Favorite favorite);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productVariant.id", source = "productVariantId")
    @Mapping(target = "user.id", source = "userId")
    Favorite favoriteFromAddRequest(AddFavoriteRequest request);

    @Mapping(target = "productVariantId", source = "productVariant.id")
    @Mapping(target = "userId", source = "user.id")
    AddFavoriteResponse addResponseFromFavorite(Favorite favorite);
}
