package com.Steprella.Steprella.services.dtos.requests.productfiles;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductFileRequest {

    @NotNull
    private int productVariantId;

    private List<MultipartFile> files;
}
