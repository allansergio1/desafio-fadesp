package br.com.bank.domain.pagamento.dto;

import br.com.bank.domain.pagamento.enums.MetodoPagamento;
import br.com.bank.util.annotation.CpfCnpj;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PagamentoDTO(
        @NotNull(message = "O código do débito é obrigatório")
        Integer codigoDebito,
        @NotBlank(message = "O CPF/CNPJ é obrigatório")
        @CpfCnpj
        String cpfCnpj,
        @NotNull(message = "O método de pagamento é obrigatório")
        MetodoPagamento metodo,
        String numeroCartao,
        @NotNull(message = "O valor do pagamento é obrigatório")
        BigDecimal valor
) {}
