package com.Steprella.Steprella.core.utils.exceptions.problemdetails;

public class InternalServerErrorDetails extends ProblemDetails{

    public InternalServerErrorDetails(String detail) {
        setDetail(detail);
        setTitle("Server error");
        setType("InternalServerErrorException");
    }
}
