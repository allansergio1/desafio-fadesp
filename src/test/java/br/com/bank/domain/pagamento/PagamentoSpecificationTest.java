package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.dto.FiltroPagamentoDTO;
import br.com.bank.domain.pagamento.enums.StatusPagamento;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoSpecificationTest {

    @Mock
    private Root<Pagamento> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder builder;

    @Mock
    private Path<Object> path;

    @Test
    @DisplayName("Deve criar specification sem filtros")
    void comFiltro_SemFiltros() {
        FiltroPagamentoDTO filtro = new FiltroPagamentoDTO(null, null, null);
        Specification<Pagamento> spec = PagamentoSpecification.comFiltro(filtro);

        assertNotNull(spec);
        Predicate predicate = spec.toPredicate(root, query, builder);
        assertNull(predicate);
    }

    @Test
    @DisplayName("Deve criar specification com filtro de código débito")
    void comFiltro_CodigoDebito() {
        when(root.get("codigoDebito")).thenReturn(path);
        when(builder.equal(path, 12345)).thenReturn(mock(Predicate.class));

        FiltroPagamentoDTO filtro = new FiltroPagamentoDTO(12345, null, null);
        Specification<Pagamento> spec = PagamentoSpecification.comFiltro(filtro);

        assertNotNull(spec);
        spec.toPredicate(root, query, builder);
        verify(builder).equal(root.get("codigoDebito"), 12345);
    }

    @Test
    @DisplayName("Deve criar specification com filtro de CPF/CNPJ")
    void comFiltro_CpfCnpj() {
        when(root.get("cpfCnpj")).thenReturn(path);
        when(builder.equal(path, "62284841090")).thenReturn(mock(Predicate.class));

        FiltroPagamentoDTO filtro = new FiltroPagamentoDTO(null, "62284841090", null);
        Specification<Pagamento> spec = PagamentoSpecification.comFiltro(filtro);

        assertNotNull(spec);
        spec.toPredicate(root, query, builder);
        verify(builder).equal(root.get("cpfCnpj"), "62284841090");
    }

    @Test
    @DisplayName("Deve criar specification com filtro de status")
    void comFiltro_Status() {
        when(root.get("status")).thenReturn(path);
        when(builder.equal(path, StatusPagamento.PENDENTE)).thenReturn(mock(Predicate.class));

        FiltroPagamentoDTO filtro = new FiltroPagamentoDTO(null, null, StatusPagamento.PENDENTE);
        Specification<Pagamento> spec = PagamentoSpecification.comFiltro(filtro);

        assertNotNull(spec);
        spec.toPredicate(root, query, builder);
        verify(builder).equal(root.get("status"), StatusPagamento.PENDENTE);
    }
}