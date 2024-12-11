package com.Steprella.Steprella.core.utils.exceptions.problemdetails;

public class ForbiddenDetails extends ProblemDetails {

    public ForbiddenDetails(String detail) {
        setDetail(detail);
        setTitle("Forbidden Rule Violation");
        setType("AccessDeniedException");
    }
}
