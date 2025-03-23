package br.com.bank.domain.pagamento.enums;

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
}
