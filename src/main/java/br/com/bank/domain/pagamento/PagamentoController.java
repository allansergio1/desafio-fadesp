package br.com.bank.domain.pagamento;

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
}
