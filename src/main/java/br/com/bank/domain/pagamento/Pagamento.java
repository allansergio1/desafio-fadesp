package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.enums.MetodoPagamento;
import br.com.bank.domain.pagamento.enums.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pagamento implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Integer codigoDebito;

    @Column(nullable = false)
    private String cpfCnpj;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodo;

    @Column(nullable = false)
    private BigDecimal valor;

    private String numeroCartao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    private boolean ativo = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigoDebito() {
        return codigoDebito;
    }

    public void setCodigoDebito(Integer codigoDebito) {
        this.codigoDebito = codigoDebito;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
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
        if (valor != null) {
            this.valor = valor.setScale(2, RoundingMode.HALF_UP);
        } else {
            this.valor = null;
        }
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
        return ativo == pagamento.ativo && Objects.equals(id, pagamento.id) && Objects.equals(codigoDebito, pagamento.codigoDebito) && Objects.equals(cpfCnpj, pagamento.cpfCnpj) && metodo == pagamento.metodo && Objects.equals(valor, pagamento.valor) && Objects.equals(numeroCartao, pagamento.numeroCartao) && status == pagamento.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoDebito, cpfCnpj, metodo, valor, numeroCartao, status, ativo);
    }
}
