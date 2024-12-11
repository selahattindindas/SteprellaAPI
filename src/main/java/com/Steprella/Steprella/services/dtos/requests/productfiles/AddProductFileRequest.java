package com.Steprella.Steprella.services.dtos.requests.productfiles;

import jakarta.validation.constraints.Min;
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
    @Min(1)
    private int productVariantId;

    @NotNull
    private List<MultipartFile> files;
}
