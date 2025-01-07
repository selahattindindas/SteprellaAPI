package com.Steprella.Steprella.services.dtos.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    private int statusCode;

    private String statusMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalCount;

    private T data;
}
