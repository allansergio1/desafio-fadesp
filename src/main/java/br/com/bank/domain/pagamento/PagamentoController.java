package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.dto.AtualizarPagamentoDTO;
import br.com.bank.domain.pagamento.dto.FiltroPagamentoDTO;
import br.com.bank.domain.pagamento.dto.PagamentoDTO;
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

    @PostMapping("/receber")
    public ResponseEntity<Pagamento> receber(@RequestBody @Valid PagamentoDTO pagamentoDTO) {
        return ResponseEntity.ok(pagamentoService.receber(pagamentoDTO));
    }

    @PostMapping("/atualizar")
    public ResponseEntity<Pagamento> atualizar(@RequestBody @Valid AtualizarPagamentoDTO atualizarDTO) {
        return ResponseEntity.ok(pagamentoService.atualizar(atualizarDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        pagamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Pagamento>> listar(@ParameterObject @ModelAttribute FiltroPagamentoDTO filtro) {
        return ResponseEntity.ok(pagamentoService.listar(filtro));
    }
}
