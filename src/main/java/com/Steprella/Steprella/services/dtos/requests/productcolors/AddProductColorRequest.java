package com.Steprella.Steprella.services.dtos.requests.productcolors;

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
public class AddProductColorRequest {

    @NotNull
    private int productId;

    @NotNull
    private int colorId;

    private List<MultipartFile> files;
}
