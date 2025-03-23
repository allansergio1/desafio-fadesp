package br.com.bank.domain.pagamento.dto;

import br.com.bank.domain.pagamento.enums.StatusPagamento;
import jakarta.validation.constraints.NotNull;

public record AtualizarPagamentoDTO(
        @NotNull(message = "O id deve ser informado e não pode ser nulo")
        Long id,
        @NotNull(message = "O status deve ser informado e não pode ser nulo")
        StatusPagamento status
) {
}
