package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.dto.AtualizarPagamentoDTO;
import br.com.bank.domain.pagamento.dto.PagamentoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/receber")
    public ResponseEntity<Pagamento> receber(@RequestBody PagamentoDTO pagamentoDTO) {
        return ResponseEntity.ok(pagamentoService.receber(pagamentoDTO));
    }

    @PostMapping("/atualizar")
    public ResponseEntity<Pagamento> atualizar(@RequestBody AtualizarPagamentoDTO atualizarDTO) {
        return ResponseEntity.ok(pagamentoService.atualizar(atualizarDTO));
    }
}
