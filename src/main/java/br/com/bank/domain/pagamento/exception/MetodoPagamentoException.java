package br.com.bank.domain.pagamento.exception;

public class MetodoPagamentoException extends RuntimeException {

    public MetodoPagamentoException(String message) {
        super(message);
    }

    public MetodoPagamentoException(String message, Throwable cause) {
        super(message, cause);
    }
}
