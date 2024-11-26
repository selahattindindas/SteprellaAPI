package com.Steprella.Steprella.core.utils.exceptions.problemdetails;

public class NotFoundDetails extends ProblemDetails{

    public NotFoundDetails(String detail) {
        setDetail(detail);
        setTitle("Resource Not Found");
        setType("NotFoundException");
    }
}
