package com.rayabhi99.projects.urlshortener.model.error;

public enum ErrorCode {
    DB_SEARCH_ERROR(ErrorOwner.DB, "Error occurred while searching in DB"),
    DB_INSERTION_ERROR(ErrorOwner.DB, "Error occurred while inserting in DB"),
    DB_DELETION_ERROR(ErrorOwner.DB, "Error occurred while deleting in DB"),
    DB_UPDATE_ERROR(ErrorOwner.DB, "Error occurred while deleting in DB"),
    URL_NOT_FOUND_ERROR(ErrorOwner.CLIENT, "URL is not found in DB"),
    URL_MALFORMED_ERROR(ErrorOwner.CLIENT, "URL is not valid and malformed"),
    INVALID_TTL_ERROR(ErrorOwner.CLIENT, "TTL is not validated. Please give a number"),
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
