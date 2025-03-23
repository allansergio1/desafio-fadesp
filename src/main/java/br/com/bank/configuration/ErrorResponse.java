package br.com.bank.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private String exceptionName;
    private String message;
    private String detailMessage;

    public ErrorResponse(String exceptionName, String message, String detailMessage) {
        this.exceptionName = exceptionName;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public String getMessage() {
        return message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Exception: ").append(this.exceptionName).append(" | ");
        if (this.message != null) {
            builder.append("Message: ").append(this.message).append(" | ");
        }
        builder.append("Detail: ").append(this.detailMessage);
        return builder.toString();
    }
}
