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

    @Mapping(target = "variant.variantId", source = "productVariant.id")
    @Mapping(target = "variant.colorName", source = "productVariant.color.name")
    @Mapping(target = "variant.rating", source = "productVariant.product.rating")
    @Mapping(target = "variant.ratingCount", source = "productVariant.product.ratingCount")
    @Mapping(target = "variant.description", source = "productVariant.product.description")
    @Mapping(target = "variant.brandName", source = "productVariant.product.brand.name")
    @Mapping(target = "variant.shoeModelName", source = "productVariant.product.shoeModel.modelName")
    @Mapping(target = "variant.materialName", source = "productVariant.product.material.name")
    @Mapping(target = "variant.usageAreaName", source = "productVariant.product.usageArea.name")
    @Mapping(target = "variant.productImages", source = "productVariant.productFiles")
    ListFavoriteResponse listResponseFromFavorite(Favorite favorite);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productVariant.id", source = "request.productVariantId")
    @Mapping(target = "customer", source = "customer")
    Favorite favoriteFromAddRequest(AddFavoriteRequest request, Customer customer);

    @Mapping(target = "productVariantId", source = "productVariant.id")
    AddFavoriteResponse addResponseFromFavorite(Favorite favorite);
}
