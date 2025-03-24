package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.dto.AtualizarPagamentoDTO;
import br.com.bank.domain.pagamento.dto.FiltroPagamentoDTO;
import br.com.bank.domain.pagamento.dto.PagamentoDTO;
import br.com.bank.domain.pagamento.enums.MetodoPagamento;
import br.com.bank.domain.pagamento.enums.StatusPagamento;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoControllerTest {

    @Mock
    private PagamentoService pagamentoService;

    @InjectMocks
    private PagamentoController pagamentoController;

    private PagamentoDTO pagamentoDTO;
    private AtualizarPagamentoDTO atualizarDTO;
    private FiltroPagamentoDTO filtroDTO;
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

        filtroDTO = new FiltroPagamentoDTO(
                12345,
                "123.456.789-09",
                StatusPagamento.PENDENTE
        );

        pagamento = new Pagamento();
        pagamento.setId(1L);
        pagamento.setCodigoDebito(12345);
        pagamento.setStatus(StatusPagamento.PENDENTE);
    }

    @Test
    @DisplayName("Deve realizar pagamento com sucesso")
    void realizarPagamento_Success() {
        when(pagamentoService.realizar(any(PagamentoDTO.class))).thenReturn(pagamento);

        ResponseEntity<Pagamento> response = pagamentoController.realizar(pagamentoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamento, response.getBody());
        verify(pagamentoService, times(1)).realizar(pagamentoDTO);
    }

    @Test
    @DisplayName("Deve atualizar pagamento com sucesso")
    void atualizarPagamento_Success() {
        when(pagamentoService.atualizar(any(AtualizarPagamentoDTO.class))).thenReturn(pagamento);

        ResponseEntity<Pagamento> response = pagamentoController.atualizar(atualizarDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamento, response.getBody());
        verify(pagamentoService, times(1)).atualizar(atualizarDTO);
    }

    @Test
    @DisplayName("Deve excluir pagamento com sucesso")
    void excluirPagamento_Success() {
        doNothing().when(pagamentoService).excluir(1L);

        ResponseEntity<Void> response = pagamentoController.excluir(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(pagamentoService, times(1)).excluir(1L);
    }

    @Test
    @DisplayName("Deve listar pagamentos com filtro com sucesso")
    void listarPagamentos_WithFilter_Success() {
        List<Pagamento> pagamentos = Collections.singletonList(pagamento);
        when(pagamentoService.listar(any(FiltroPagamentoDTO.class))).thenReturn(pagamentos);

        ResponseEntity<List<Pagamento>> response = pagamentoController.listar(filtroDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentos, response.getBody());
        verify(pagamentoService, times(1)).listar(filtroDTO);
    }

    @Test
    @DisplayName("Deve listar pagamentos sem filtro com sucesso")
    void listarPagamentos_WithoutFilter_Success() {
        List<Pagamento> pagamentos = Collections.singletonList(pagamento);
        when(pagamentoService.listar(isNull())).thenReturn(pagamentos);

        ResponseEntity<List<Pagamento>> response = pagamentoController.listar(null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagamentos, response.getBody());
        verify(pagamentoService, times(1)).listar(null);
    }
}