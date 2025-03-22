package br.com.bank.domain.pagamento;

public enum StatusPagamento {

    PENDENTE("Pendente de processamento"),
    FALHA("Processado com falha"),
    SUCESSO("Processado com sucesso");

    private String descricao;

    StatusPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
