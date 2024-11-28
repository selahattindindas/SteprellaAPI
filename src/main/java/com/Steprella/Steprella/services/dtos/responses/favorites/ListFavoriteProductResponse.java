package com.Steprella.Steprella.services.dtos.responses.favorites;

import com.Steprella.Steprella.services.dtos.responses.files.ListFileResponse;
import com.Steprella.Steprella.services.dtos.responses.products.ListProductResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListFavoriteProductResponse extends ListProductResponse {

    private String colorName;

    private String createdDate;

    private String updatedDate;

    private List<ListFileResponse> productFiles;
}
