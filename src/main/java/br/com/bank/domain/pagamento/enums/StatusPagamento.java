package br.com.bank.domain.pagamento.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public enum StatusPagamento {

    PENDENTE("Pendente de processamento"),
    PROCESSADO_COM_FALHA("Processado com falha"),
    PROCESSADO("Processado com sucesso");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @JsonCreator
    public static StatusPagamento fromString(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        try {
            return StatusPagamento.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status de pagamento inválido: " + value
                    + ". Valores aceitos: " + Arrays.toString(values()));
        }
    }
}
