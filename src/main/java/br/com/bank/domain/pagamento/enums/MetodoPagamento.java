package br.com.bank.domain.pagamento.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public enum MetodoPagamento {

    BOLETO,
    CARTAO_CREDITO,
    CARTAO_DEBITO,
    PIX;

    MetodoPagamento() {
    }

    public static List<MetodoPagamento> getMetodosCartao() {
        return List.of(CARTAO_CREDITO, CARTAO_DEBITO);
    }

    @JsonCreator
    public static MetodoPagamento fromString(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        try {
            return MetodoPagamento.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Método de pagamento inválido: " + value
                    + ". Valores aceitos: " + Arrays.toString(values()));
        }
    }
}
