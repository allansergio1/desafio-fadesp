package br.com.bank.domain.pagamento.dto;

import br.com.bank.domain.pagamento.enums.StatusPagamento;

public record FiltroPagamentoDTO(
        Integer codigoDebito,
        String cpfCnpj,
        StatusPagamento status
) {
}
