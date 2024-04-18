package com.hbo.smart.serverapi.exception;

import com.hbo.common.rest.client.exception.RestException;
import lombok.Getter;

@Getter
public class SmartException extends RestException {

    private Object rules;

    /**
     * Constructor.
     *
     * @param status           The http status
     * @param message          The primary message indicating the error condition
     * @param details          A detailed variant of our message
     * @param developerMessage Developer details like a stack trace
     * @param rules            validation errors
     */
    public SmartException(int status, String message, String details, String developerMessage, Object rules) {
        super(status, message, details, developerMessage);
        this.rules = rules;
    }
}
