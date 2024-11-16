package com.Steprella.Steprella.services.dtos.responses.productcolors;

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
public class ListProductColorResponse {

    private int id;

    private int productId;

    private String colorName;

    private List<ListFileResponse> colorFiles;
}