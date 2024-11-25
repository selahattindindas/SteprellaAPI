package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Favorite;
import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FavoriteMapper {

    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    @Mapping(target = "productVariant.colorName", source = "productVariant.color.name")
    @Mapping(target = "productVariant.productFiles", source = "productVariant.productFiles")
    @Mapping(target = "productVariant.category", source = "productVariant.product.category")
    @Mapping(target = "productVariant.price", source = "productVariant.product.price")
    @Mapping(target = "productVariant.description", source = "productVariant.product.description")
    @Mapping(target = "productVariant.brandName", source = "productVariant.product.brand.name")
    @Mapping(target = "productVariant.shoeModelName", source = "productVariant.product.shoeModel.modelName")
    @Mapping(target = "productVariant.rating", source = "productVariant.rating")
    @Mapping(target = "productVariant.ratingCount", source = "productVariant.ratingCount")
    ListFavoriteResponse listResponseFromFavorite(Favorite favorite);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productVariant.id", source = "productVariantId")
    @Mapping(target = "user.id", source = "userId")
    Favorite favoriteFromAddRequest(AddFavoriteRequest request);

    @Mapping(target = "productVariantId", source = "productVariant.id")
    @Mapping(target = "userId", source = "user.id")
    AddFavoriteResponse addResponseFromFavorite(Favorite favorite);
}
