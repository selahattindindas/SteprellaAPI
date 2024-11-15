package com.Steprella.Steprella.services.abstracts;

import com.Steprella.Steprella.services.dtos.responses.files.FileDetailResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface FileService {

    CompletableFuture<List<FileDetailResponse>> uploadFilesAsync(String path, List<MultipartFile> files);

    void delete(int id);
}
