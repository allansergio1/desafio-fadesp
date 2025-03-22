package br.com.bank.domain.pagamento.dto;

import br.com.bank.domain.pagamento.enums.MetodoPagamento;
import java.math.BigDecimal;

public record PagamentoDTO(
        String cpfCnpj,
        MetodoPagamento metodo,
        String numeroCartao,
        BigDecimal valor
) {
}
