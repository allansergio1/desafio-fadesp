package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.dto.PagamentoDTO;
import br.com.bank.domain.pagamento.enums.MetodoPagamento;
import br.com.bank.domain.pagamento.enums.StatusPagamento;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Pagamento {

    @Id
    @GeneratedValue
    private Long codigoDebito;
    private String cpfCnpjPagador;
    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodo;
    private BigDecimal valor;
    private String numeroCartao;
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;
    private boolean ativo = true;

    public Long getCodigoDebito() {
        return codigoDebito;
    }

    public void setCodigoDebito(Long codigoDebito) {
        this.codigoDebito = codigoDebito;
    }

    public String getCpfCnpjPagador() {
        return cpfCnpjPagador;
    }

    public void setCpfCnpjPagador(String cpfCnpj) {
        this.cpfCnpjPagador = cpfCnpj;
    }

    public MetodoPagamento getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPagamento metodo) {
        this.metodo = metodo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        return ativo == pagamento.ativo && Objects.equals(codigoDebito, pagamento.codigoDebito)
                && Objects.equals(cpfCnpjPagador, pagamento.cpfCnpjPagador) && metodo == pagamento.metodo
                && Objects.equals(valor, pagamento.valor) && Objects.equals(numeroCartao, pagamento.numeroCartao)
                && status == pagamento.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoDebito, cpfCnpjPagador, metodo, valor, numeroCartao, status, ativo);
    }

    public void fromDTO(PagamentoDTO pagamentoDTO) {
        this.setCpfCnpjPagador(pagamentoDTO.cpfCnpj());
        this.setMetodo(pagamentoDTO.metodo());
        this.setValor(pagamentoDTO.valor());
        this.setNumeroCartao(pagamentoDTO.numeroCartao());
    }
}
