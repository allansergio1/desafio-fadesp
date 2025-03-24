package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.dto.AtualizarPagamentoDTO;
import br.com.bank.domain.pagamento.dto.FiltroPagamentoDTO;
import br.com.bank.domain.pagamento.dto.PagamentoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pagamento")
@Tag(name = "Pagamento", description = "API para realizar pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/realizar")
    @Operation(summary = "Realiza um pagamento a partir de PagamentoDTO")
    public ResponseEntity<Pagamento> realizar(@RequestBody @Valid PagamentoDTO pagamentoDTO) {
        return ResponseEntity.ok(pagamentoService.realizar(pagamentoDTO));
    }

    @PostMapping("/atualizar")
    @Operation(summary = "Atualiza um pagamento a partir de AtualizarPagamentoDTO")
    public ResponseEntity<Pagamento> atualizar(@RequestBody @Valid AtualizarPagamentoDTO atualizarDTO) {
        return ResponseEntity.ok(pagamentoService.atualizar(atualizarDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um pagamento a partir de seu id")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pagamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    @Operation(summary = "Lista todos os pagamentos ou filtra a partir dos parâmetros recebidos")
    public ResponseEntity<List<Pagamento>> listar(@ParameterObject @ModelAttribute FiltroPagamentoDTO filtro) {
        return ResponseEntity.ok(pagamentoService.listar(filtro));
    }
}
