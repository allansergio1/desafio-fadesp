package br.com.bank.domain.pagamento.dto;

import br.com.bank.domain.pagamento.enums.MetodoPagamento;
import java.math.BigDecimal;

public record PagamentoDTO(
        Integer codigoDebito,
        String cpfCnpj,
        MetodoPagamento metodo,
        String numeroCartao,
        BigDecimal valor
) {
}
