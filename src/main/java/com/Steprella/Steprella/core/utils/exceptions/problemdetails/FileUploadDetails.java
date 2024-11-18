package com.Steprella.Steprella.core.utils.exceptions.problemdetails;

public class FileUploadDetails extends ProblemDetails{

    public FileUploadDetails(String detail) {
        setDetail(detail);
        setTitle("File Upload Rule Violation");
        setType("FileUploadException");
    }
}
