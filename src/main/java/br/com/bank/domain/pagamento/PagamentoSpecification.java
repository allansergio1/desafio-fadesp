package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.dto.FiltroPagamentoDTO;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PagamentoSpecification {

    public static Specification<Pagamento> comFiltro(FiltroPagamentoDTO filtro) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filtro.codigoDebito() != null) {
                predicates.add(builder.equal(root.get("codigoDebito"), filtro.codigoDebito()));
            }
            if (filtro.cpfCnpj() != null) {
                predicates.add(builder.equal(root.get("cpfCnpj"), filtro.cpfCnpj()));
            }
            if (filtro.status() != null) {
                predicates.add(builder.equal(root.get("status"), filtro.status()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
