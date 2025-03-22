package br.com.bank.domain.pagamento;

import java.math.BigDecimal;

public record PagamentoDTO(
        Integer codigoDebito,
        String cpfCnpj,
        MetodoPagamento metodo,
        String numeroCartao,
        BigDecimal valor
) {
}
