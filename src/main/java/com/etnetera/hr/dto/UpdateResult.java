package com.etnetera.hr.dto;

import lombok.Getter;

/**
 * According to https://greenbytes.de/tech/webdav/rfc7231.html#PUT
 * the PUT should result either in 200/204 or 201 in case the entity being updated doesn't exist and gets created.
 */
public enum UpdateResult {

    UPDATED_OK(200),
    CREATED(201),
    UPDATED_NO_CONTENT(204);

    @Getter
    private final int statusCode;

    UpdateResult(int statusCode) {
        this.statusCode = statusCode;
    }
}
