package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.entities.concretes.ProductFile;
import com.Steprella.Steprella.repositories.ProductFileRepository;
import com.Steprella.Steprella.services.abstracts.FileService;
import com.Steprella.Steprella.services.abstracts.ProductFileService;
import com.Steprella.Steprella.services.dtos.requests.productfiles.AddProductFileRequest;
import com.Steprella.Steprella.services.dtos.responses.files.FileDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.files.ListFileResponse;
import com.Steprella.Steprella.services.dtos.responses.productfiles.AddProductFileResponse;
import com.Steprella.Steprella.services.dtos.responses.productfiles.ListProductFileResponse;
import com.Steprella.Steprella.services.mappers.ProductFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ProductFileServiceImpl implements ProductFileService {

    private final ProductFileRepository productFileRepository;
    private final FileService fileService;

    @Override
    public ListProductFileResponse getByProductVariantId(int productVariantId) {
        List<ProductFile> productFiles = productFileRepository.findByProductVariantId(productVariantId);
        List<ListFileResponse> fileResponses = ProductFileMapper.INSTANCE.listResponseFromFileResponses(productFiles);

        return ProductFileMapper.INSTANCE.listResponseByProductVariantId(
                productFiles.isEmpty() ? null : productFiles.getFirst(), fileResponses);
    }

    @Override
    public AddProductFileResponse add(AddProductFileRequest request) throws IOException {
        ProductFile initialFile = ProductFileMapper.INSTANCE.productFileFromAddRequest(request);

        List<MultipartFile> files = request.getFiles();
        CompletableFuture<List<FileDetailResponse>> uploadResult = fileService.uploadFilesAsync("photo", files);

        List<FileDetailResponse> fileDetails = uploadResult.join();

        List<ProductFile> roomTypeImages = ProductFileMapper.INSTANCE.listProductFiles(fileDetails, initialFile);

        List<ProductFile> savedImages = productFileRepository.saveAll(roomTypeImages);

        return ProductFileMapper.INSTANCE.addResponseFromProductFile(request, savedImages);
    }

    @Override
    public void delete(int id) {
        ProductFile productFile = productFileRepository.findById(id).orElse(null);
        assert productFile != null;
        productFileRepository.delete(productFile);
    }

    @Override
    public List<ListFileResponse> getResponse(List<ProductFile> productFiles) {
        return ProductFileMapper.INSTANCE.listResponseFromFileResponses(productFiles);
    }
}
