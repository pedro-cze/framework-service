package com.etnetera.hr.error;

import lombok.Getter;

@Getter
public abstract class JavaScriptFrameworkException extends RuntimeException {

    public JavaScriptFrameworkException(String message) {
        super(message);
    }
}
