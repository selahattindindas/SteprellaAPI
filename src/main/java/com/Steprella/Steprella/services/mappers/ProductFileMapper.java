package com.Steprella.Steprella.services.mappers;

import com.Steprella.Steprella.entities.concretes.ProductFile;
import com.Steprella.Steprella.services.dtos.requests.productfiles.AddProductFileRequest;
import com.Steprella.Steprella.services.dtos.responses.files.FileDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.files.ListFileResponse;
import com.Steprella.Steprella.services.dtos.responses.productfiles.AddProductFileResponse;
import com.Steprella.Steprella.services.dtos.responses.productfiles.ListProductFileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProductFileMapper {

    ProductFileMapper INSTANCE = Mappers.getMapper(ProductFileMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "fileName", source = "fileName")
    @Mapping(target = "path", source = "path")
    ListFileResponse listResponseFromProductFile(ProductFile productFile);

    @Mappings({
            @Mapping(target = "productVariantId", source = "productFile.productVariant.id"),
            @Mapping(target = "files", source = "fileResponses")
    })
    ListProductFileResponse listResponseByProductVariantId(ProductFile productFile, List<ListFileResponse> fileResponses);

    @Mappings({
            @Mapping(target = "fileName", source = "fileName"),
            @Mapping(target = "path", source = "pathOrContainerName"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "productVariant", ignore = true)
    })
    ProductFile listResponseFromFileDetail(FileDetailResponse fileDetail);

    @Mapping(target = "productVariant.id", source = "productVariantId")
    ProductFile productFileFromAddRequest(AddProductFileRequest request);

    @Mapping(target = "productVariantId", source = "request.productVariantId")
    @Mapping(target = "files", source = "savedFile")
    AddProductFileResponse addResponseFromProductFile(AddProductFileRequest request, List<ProductFile> savedFile);

    List<ListFileResponse> listResponseFromFileResponses(List<ProductFile> productFiles);

    default List<ProductFile> listProductFiles(List<FileDetailResponse> fileDetails, ProductFile existingFile) {
        List<ProductFile> list = new ArrayList<>();
        for (FileDetailResponse detail : fileDetails) {
            ProductFile newFile = listResponseFromFileDetail(detail);
            newFile.setProductVariant(existingFile.getProductVariant());
            list.add(newFile);
        }
        return list;
    }
}
