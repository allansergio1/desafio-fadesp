package br.com.bank.domain.pagamento.enums;

public enum StatusPagamento {

    PENDENTE("Pendente de processamento"),
    PROCESSADO_COM_FALHA("Processado com falha"),
    PROCESSADO("Processado com sucesso");

    private final String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
