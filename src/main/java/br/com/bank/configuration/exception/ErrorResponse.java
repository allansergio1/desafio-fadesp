package br.com.bank.configuration.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String exception;
    private String message;

    public ErrorResponse(String exception, String message) {
        this.exception = exception;
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Exception: ").append(this.exception);
        if (this.message != null) {
            builder.append(" | Message: ").append(this.message);
        }
        return builder.toString();
    }
}
