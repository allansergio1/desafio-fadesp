package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.dto.AtualizarPagamentoDTO;
import br.com.bank.domain.pagamento.dto.FiltroPagamentoDTO;
import br.com.bank.domain.pagamento.dto.PagamentoDTO;
import br.com.bank.domain.pagamento.enums.MetodoPagamento;
import br.com.bank.domain.pagamento.enums.StatusPagamento;
import br.com.bank.domain.pagamento.exception.MetodoPagamentoException;
import br.com.bank.domain.pagamento.exception.StatusPagamentoException;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;

    private PagamentoDTO pagamentoDTO;
    private AtualizarPagamentoDTO atualizarDTO;
    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        pagamentoDTO = new PagamentoDTO(
                12345,
                "123.456.789-09",
                MetodoPagamento.PIX,
                null,
                new BigDecimal("100.50")
        );

        atualizarDTO = new AtualizarPagamentoDTO(1L, StatusPagamento.PROCESSADO);

        pagamento = new Pagamento();
        pagamento.setId(1L);
        pagamento.setCodigoDebito(12345);
        pagamento.setCpfCnpj("123.456.789-09");
        pagamento.setMetodo(MetodoPagamento.PIX);
        pagamento.setValor(new BigDecimal("100.50"));
        pagamento.setStatus(StatusPagamento.PENDENTE);
        pagamento.setAtivo(true);
    }

    @Test
    @DisplayName("Deve realizar pagamento com sucesso")
    void realizarPagamento_Success() {
        when(pagamentoRepository.findByCodigoDebito(anyInt())).thenReturn(Optional.empty());
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        Pagamento result = pagamentoService.realizar(pagamentoDTO);

        assertNotNull(result);
        assertEquals(StatusPagamento.PENDENTE, result.getStatus());
        verify(pagamentoRepository, times(1)).save(any(Pagamento.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando código débito já existe")
    void realizarPagamento_DuplicateCodigoDebito() {
        when(pagamentoRepository.findByCodigoDebito(anyInt())).thenReturn(Optional.of(pagamento));

        assertThrows(EntityExistsException.class, () -> {
            pagamentoService.realizar(pagamentoDTO);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando método cartão sem número")
    void realizarPagamento_CartaoSemNumero() {
        pagamentoDTO = new PagamentoDTO(
                12345,
                "123.456.789-09",
                MetodoPagamento.CARTAO_CREDITO,
                null,
                new BigDecimal("100.50")
        );

        assertThrows(MetodoPagamentoException.class, () -> {
            pagamentoService.realizar(pagamentoDTO);
        });
    }

    @Test
    @DisplayName("Deve atualizar status do pagamento com sucesso")
    void atualizarPagamento_Success() {
        when(pagamentoRepository.findById(anyLong())).thenReturn(Optional.of(pagamento));
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamento);

        Pagamento result = pagamentoService.atualizar(atualizarDTO);

        assertEquals(atualizarDTO.status(), result.getStatus());
        verify(pagamentoRepository, times(1)).save(pagamento);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar pagamento não encontrado")
    void atualizarPagamento_NotFound() {
        when(pagamentoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            pagamentoService.atualizar(atualizarDTO);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar pagamento já processado")
    void atualizarPagamento_JaProcessado() {
        pagamento.setStatus(StatusPagamento.PROCESSADO);
        when(pagamentoRepository.findById(anyLong())).thenReturn(Optional.of(pagamento));

        assertThrows(StatusPagamentoException.class, () -> {
            pagamentoService.atualizar(atualizarDTO);
        });
    }

    @Test
    @DisplayName("Deve excluir pagamento pendente com sucesso")
    void excluirPagamento_Success() {
        when(pagamentoRepository.findById(anyLong())).thenReturn(Optional.of(pagamento));

        pagamentoService.excluir(1L);

        assertFalse(pagamento.isAtivo());
        verify(pagamentoRepository, times(1)).save(pagamento);
    }

    @Test
    @DisplayName("Deve lançar exceção ao excluir pagamento não encontrado")
    void excluirPagamento_NotFound() {
        when(pagamentoRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            pagamentoService.excluir(1L);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao excluir pagamento já processado")
    void excluirPagamento_JaProcessado() {
        pagamento.setStatus(StatusPagamento.PROCESSADO);
        when(pagamentoRepository.findById(anyLong())).thenReturn(Optional.of(pagamento));

        assertThrows(StatusPagamentoException.class, () -> {
            pagamentoService.excluir(1L);
        });
    }

    @Test
    @DisplayName("Deve buscar pagamento por ID com sucesso")
    void buscarPorId_Success() {
        when(pagamentoRepository.findById(anyLong())).thenReturn(Optional.of(pagamento));

        Optional<Pagamento> result = pagamentoService.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(pagamento, result.get());
    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar ID inexistente")
    void buscarPorId_NotFound() {
        when(pagamentoRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Pagamento> result = pagamentoService.buscarPorId(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("Deve listar pagamentos com filtro")
    void listarPagamentos_WithFilter() {
        FiltroPagamentoDTO filtro = new FiltroPagamentoDTO(
                12345, "123.456.789-09", StatusPagamento.PENDENTE);

        // Correção: usar any(Specification.class) ao invés de any()
        when(pagamentoRepository.findAll(any(Specification.class))).thenReturn(List.of(pagamento));

        List<Pagamento> result = pagamentoService.listar(filtro);

        assertEquals(1, result.size());
        assertEquals(pagamento, result.get(0));
    }

    @Test
    @DisplayName("Deve converter DTO para Entity corretamente")
    void convertToEntity_Success() {
        Pagamento result = pagamentoService.convertToEntity(pagamentoDTO);

        assertNotNull(result);
        assertEquals(pagamentoDTO.codigoDebito(), result.getCodigoDebito());
        assertEquals(pagamentoDTO.cpfCnpj(), result.getCpfCnpj());
        assertEquals(pagamentoDTO.metodo(), result.getMetodo());
        assertEquals(pagamentoDTO.valor(), result.getValor());
        assertEquals(pagamentoDTO.numeroCartao(), result.getNumeroCartao());
    }
}