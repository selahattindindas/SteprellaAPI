package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.entities.concretes.ProductFile;
import com.Steprella.Steprella.services.dtos.requests.productfiles.AddProductFileRequest;
import com.Steprella.Steprella.services.dtos.responses.files.ListFileResponse;
import com.Steprella.Steprella.services.dtos.responses.productfiles.AddProductFileResponse;
import com.Steprella.Steprella.services.dtos.responses.productfiles.ListProductFileResponse;

import java.io.IOException;
import java.util.List;

public interface ProductFileService {

    ListProductFileResponse getByProductVariantId(int productVariantId);

    AddProductFileResponse add(AddProductFileRequest request) throws IOException;

    void delete(int id);

    List<ListFileResponse> getResponse(List<ProductFile> productFiles);
}
