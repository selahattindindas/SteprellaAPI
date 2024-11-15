package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.services.abstracts.FileService;
import com.Steprella.Steprella.services.dtos.responses.files.FileDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    @Override
    public CompletableFuture<List<FileDetailResponse>> uploadFilesAsync(String path, List<MultipartFile> files) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
