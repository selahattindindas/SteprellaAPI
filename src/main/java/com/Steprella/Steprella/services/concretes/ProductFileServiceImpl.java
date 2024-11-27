package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.NotFoundException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.ProductFile;
import com.Steprella.Steprella.repositories.ProductFileRepository;
import com.Steprella.Steprella.services.abstracts.FileService;
import com.Steprella.Steprella.services.abstracts.ProductFileService;
import com.Steprella.Steprella.services.abstracts.ProductVariantService;
import com.Steprella.Steprella.services.dtos.requests.productfiles.AddProductFileRequest;
import com.Steprella.Steprella.services.dtos.responses.files.FileDetailResponse;
import com.Steprella.Steprella.services.dtos.responses.files.ListFileResponse;
import com.Steprella.Steprella.services.dtos.responses.productfiles.AddProductFileResponse;
import com.Steprella.Steprella.services.dtos.responses.productfiles.ListProductFileResponse;
import com.Steprella.Steprella.services.mappers.ProductFileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductFileServiceImpl implements ProductFileService {

    private final ProductFileRepository productFileRepository;
    private final FileService fileService;
    private final ProductVariantService productVariantService;

    @Autowired
    public ProductFileServiceImpl(@Lazy ProductVariantService productVariantService,
                                  FileService fileService,
                                  ProductFileRepository productFileRepository){
        this.productVariantService = productVariantService;
        this.fileService = fileService;
        this.productFileRepository = productFileRepository;
    }

    @Override
    public ListProductFileResponse getByProductVariantId(int productVariantId) {
        productVariantService.getById(productVariantId);

        List<ProductFile> productFiles = productFileRepository.findByProductVariantId(productVariantId);
        List<ListFileResponse> fileResponses = ProductFileMapper.INSTANCE.listResponseFromFileResponses(productFiles);

        return ProductFileMapper.INSTANCE.listResponseByProductVariantId(
                productFiles.isEmpty() ? null : productFiles.getFirst(), fileResponses);
    }

    @Override
    public AddProductFileResponse add(AddProductFileRequest request) throws IOException {
        productVariantService.getById(request.getProductVariantId());

        ProductFile initialFile = ProductFileMapper.INSTANCE.productFileFromAddRequest(request);

        List<MultipartFile> files = request.getFiles();
        CompletableFuture<List<FileDetailResponse>> uploadResult = fileService.uploadFilesAsync("photo", files);

        List<FileDetailResponse> fileDetails = uploadResult.join();

        List<ProductFile> productFile = ProductFileMapper.INSTANCE.listProductFiles(fileDetails, initialFile);

        List<ProductFile> savedFile = productFileRepository.saveAll(productFile);

        return ProductFileMapper.INSTANCE.addResponseFromProductFile(request, savedFile);
    }

    @Override
    public void delete(int id) {
        ProductFile productFile = productFileRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException(Messages.Error.CUSTOM_FILE_NOT_FOUND));
        productFileRepository.delete(productFile);
    }

    @Override
    public List<ListFileResponse> getResponse(List<ProductFile> productFiles) {
        return ProductFileMapper.INSTANCE.listResponseFromFileResponses(productFiles);
    }

    @Override
    public List<ProductFile> getResponseByProductVariantId(int productVariantId) {
        return productFileRepository.findByProductVariantId(productVariantId);
    }
}
