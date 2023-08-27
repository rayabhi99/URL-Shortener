package com.rayabhi99.projects.urlshortener.model.error;
import com.google.common.base.Throwables;

public class UrlShortenerException extends RuntimeException{
    private ErrorCode errorCode;
    private ErrorOwner errorOwner;
    private String errorMessage;
    private Throwable throwable;

    public UrlShortenerException(ErrorCode errorCode, String errorMessage, Throwable throwable) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorOwner = errorCode.getErrorOwner();
        this.errorMessage = errorMessage;
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("errorCode: ").append(errorCode).
                append("errorOwner: ").append(errorOwner).
                append("errorMessage: ").append(errorMessage).
                append("exception: ").append(Throwables.getStackTraceAsString(throwable)).
                toString();
    }
}
