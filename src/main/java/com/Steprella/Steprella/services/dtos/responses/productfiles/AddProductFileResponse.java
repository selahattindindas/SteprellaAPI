package com.Steprella.Steprella.services.dtos.responses.productfiles;

import com.Steprella.Steprella.services.dtos.responses.files.ListFileResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductFileResponse {

    private int productVariantId;

    private List<ListFileResponse> files;
}
