package com.Steprella.Steprella.core.utils.exceptions.problemdetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessProblemDetails extends ProblemDetails{

    public BusinessProblemDetails(String detail) {
        setDetail(detail);
        setTitle("Business Rule Violation");
        setType("BusinessException");
    }
}
