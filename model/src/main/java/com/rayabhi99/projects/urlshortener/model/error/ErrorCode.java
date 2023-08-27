package com.rayabhi99.projects.urlshortener.model.error;

public enum ErrorCode {
    DB_SEARCH_ERROR(ErrorOwner.DB, "Error occurred while searching in DB"),
    DB_INSERTION_ERROR(ErrorOwner.DB, "Error occurred while inserting in DB"),
    DB_DELETION_ERROR(ErrorOwner.DB, "Error occurred while deleting in DB"),
    DB_UPDATE_ERROR(ErrorOwner.DB, "Error occurred while deleting in DB"),
    INVALID_TTL_ERROR(ErrorOwner.CLIENT, "TTL is not validated. Please give a number"),
    TTL_MAX_LIMIT_CROSSED(ErrorOwner.CLIENT, "Max TTL limit is crossed"),
    INVALID_STATUS(ErrorOwner.CLIENT, "The status mentioned in invalid"),
    SHORT_URL_ALREADY_EXISTS(ErrorOwner.CLIENT, "Short Url already exists"),
    URL_NOT_FOUND_ERROR(ErrorOwner.CLIENT, "URL is not found in DB"),
    URL_MALFORMED_ERROR(ErrorOwner.CLIENT, "URL is not valid and malformed"),

    ;

    private final ErrorOwner errorOwner;
    private final String errorDescription;

    public ErrorOwner getErrorOwner() {
        return errorOwner;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    ErrorCode(ErrorOwner errorOwner, String errorDescription) {
        this.errorOwner = errorOwner;
        this.errorDescription = errorDescription;
    }
}
