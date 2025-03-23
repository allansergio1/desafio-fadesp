package br.com.bank.domain.pagamento.exception;

public class StatusPagamentoException extends RuntimeException {

    public StatusPagamentoException(String message) {
        super(message);
    }

    public StatusPagamentoException(String message, Throwable cause) {
        super(message, cause);
    }
}
