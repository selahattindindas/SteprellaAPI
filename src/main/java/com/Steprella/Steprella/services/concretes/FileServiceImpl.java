package com.Steprella.Steprella.services.concretes;

import com.Steprella.Steprella.core.utils.exceptions.types.FileUploadException;
import com.Steprella.Steprella.core.utils.messages.Messages;
import com.Steprella.Steprella.entities.concretes.File;
import com.Steprella.Steprella.repositories.FileRepository;
import com.Steprella.Steprella.services.abstracts.FileService;
import com.Steprella.Steprella.services.dtos.responses.files.FileDetailResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Value("${spring.file.dir}")
    private String baseUploadDir;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Override
    public CompletableFuture<List<FileDetailResponse>> uploadFilesAsync(String path, List<MultipartFile> files) throws IOException {
        Path uploadLocation = Paths.get(baseUploadDir, path);

        createDirectoriesIfNotExist(uploadLocation);

        long maxSizeInBytes = parseMaxFileSize(maxFileSize);

        List<CompletableFuture<FileDetailResponse>> uploadTasks = files.stream()
                .map(file -> {
                    if (file.getSize() > maxSizeInBytes) {
                        throw new FileUploadException(String.format(Messages.Error.FILE_SIZE_EXCEEDED, maxFileSize));
                    }
                    return uploadFile(uploadLocation, file, path);
                })
                .toList();

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(uploadTasks.toArray(new CompletableFuture[0]));

        return allFutures.thenApply(v ->
                uploadTasks.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
    }

    @Override
    @Transactional
    public void delete(int id) {
        File file = fileRepository.findById(id).orElse(null);

        Path imagePath = Paths.get(baseUploadDir, file != null ? file.getPath().replace("/", "\\") : null);
        deleteFileSilently(imagePath);
        fileRepository.deleteById(id);
    }

    private long parseMaxFileSize(String maxFileSize) {
        if (maxFileSize.endsWith("MB")) {
            return Long.parseLong(maxFileSize.replace("MB", "")) * 1024 * 1024;
        } else if (maxFileSize.endsWith("KB")) {
            return Long.parseLong(maxFileSize.replace("KB", "")) * 1024;
        } else {
            return Long.parseLong(maxFileSize);
        }
    }

    private void createDirectoriesIfNotExist(Path uploadLocation) throws IOException {
        if (!Files.exists(uploadLocation)) {
            Files.createDirectories(uploadLocation);
        }
    }

    private CompletableFuture<FileDetailResponse> uploadFile(Path uploadLocation, MultipartFile file, String directoryPath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String originalFileName = file.getOriginalFilename();
                String uniqueFileName = generateUniqueFileName(uploadLocation, originalFileName);
                Path targetLocation = uploadLocation.resolve(uniqueFileName);
                Files.copy(file.getInputStream(), targetLocation);

                return createFileDetailResponse(directoryPath, uniqueFileName);
            } catch (IOException e) {
                throw new FileUploadException(String.format(Messages.Error.FILE_UPLOAD_FAILED, file.getOriginalFilename()), e);
            }
        });
    }

    private String generateUniqueFileName(Path uploadLocation, String originalFileName) {
        String fileBaseName = Paths.get(originalFileName).getFileName().toString().replaceFirst("[.][^.]+$", "").replaceAll("\\s", "_");
        String fileExtension = "." + originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        String uniqueFileName;
        int counter = 1;

        do {
            uniqueFileName = String.format("%s-%d%s", fileBaseName, counter++, fileExtension);
        } while (Files.exists(uploadLocation.resolve(uniqueFileName)));

        return uniqueFileName;
    }

    private FileDetailResponse createFileDetailResponse(String relativePath, String fileName) {
        FileDetailResponse response = new FileDetailResponse();
        response.setFileName(fileName);
        response.setPathOrContainerName(relativePath.replace("\\", "/") + "/" + fileName);
        return response;
    }

    private void deleteFileSilently(Path path) {
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            throw new FileUploadException(String.format(Messages.Error.FILE_DELETION_FAILED, path), e);

        }
    }
}
