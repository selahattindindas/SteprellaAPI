package com.Steprella.Steprella.services.dtos.responses.files;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDetailResponse {

    private String fileName;

    private String pathOrContainerName;
}
