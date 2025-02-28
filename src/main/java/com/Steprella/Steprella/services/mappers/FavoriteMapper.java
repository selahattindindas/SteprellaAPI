package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.Favorite;
import com.Steprella.Steprella.entities.concretes.Customer;
import com.Steprella.Steprella.entities.concretes.Product;
import com.Steprella.Steprella.services.dtos.requests.favorites.AddFavoriteRequest;
import com.Steprella.Steprella.services.dtos.responses.favorites.AddFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.favorites.ListFavoriteResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import com.Steprella.Steprella.services.dtos.responses.productvariants.ListProductVariantResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = {ProductMapper.class})
public interface FavoriteMapper {

    FavoriteMapper INSTANCE = Mappers.getMapper(FavoriteMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "product", expression = "java(mapProductWithActiveVariants(favorite))")
    ListFavoriteResponse listResponseFromFavorite(Favorite favorite);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productVariant.id", source = "request.productVariantId")
    @Mapping(target = "customer", source = "customer")
    Favorite favoriteFromAddRequest(AddFavoriteRequest request, Customer customer);

    @Mapping(target = "productVariantId", source = "productVariant.id")
    AddFavoriteResponse addResponseFromFavorite(Favorite favorite);

    default ListProductResponse mapProductWithActiveVariants(Favorite favorite) {
        Product product = favorite.getProductVariant().getProduct();
        ListProductResponse response = ProductMapper.INSTANCE.listResponseFromProduct(product, Collections.emptyList());

        List<ListProductVariantResponse> activeVariants = response.getProductVariants().stream()
                .filter(ListProductVariantResponse::isActive)
                .collect(Collectors.toList());

        response.setProductVariants(activeVariants);
        return response;
    }
}
