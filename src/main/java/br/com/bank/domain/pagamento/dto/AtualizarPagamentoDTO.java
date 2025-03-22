package br.com.bank.domain.pagamento.dto;

import br.com.bank.domain.pagamento.enums.StatusPagamento;

public record AtualizarPagamentoDTO(
        Long id,
        StatusPagamento status
) {
}
