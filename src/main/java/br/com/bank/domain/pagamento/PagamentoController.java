package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.dto.AtualizarPagamentoDTO;
import br.com.bank.domain.pagamento.dto.FiltroPagamentoDTO;
import br.com.bank.domain.pagamento.dto.PagamentoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        Pagamento pagamento = pagamentoService.realizar(pagamentoDTO);
        pagamento.add(linkTo(methodOn(PagamentoController.class).buscar(pagamento.getId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualiza um pagamento a partir de AtualizarPagamentoDTO")
    public ResponseEntity<Pagamento> atualizar(@RequestBody @Valid AtualizarPagamentoDTO atualizarDTO) {
        Pagamento pagamento = pagamentoService.atualizar(atualizarDTO);
        pagamento.add(linkTo(methodOn(PagamentoController.class).buscar(pagamento.getId())).withSelfRel());
        pagamento.add(linkTo(methodOn(PagamentoController.class).excluir(pagamento.getId()))
                .withRel("excluir").withType("DELETE"));
        pagamento.add(linkTo(methodOn(PagamentoController.class)
                .listar(new FiltroPagamentoDTO(null, null, null)))
                .withRel("listar").withType("GET"));
        return ResponseEntity.ok(pagamento);
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
        List<Pagamento> pagamentos = pagamentoService.listar(filtro).stream()
                .map(pagamento -> {
                    pagamento.add(linkTo(methodOn(PagamentoController.class).buscar(pagamento.getId()))
                            .withSelfRel().withType("GET"));
                    return pagamento;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(pagamentos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um pagamento a partir de seu id")
    public ResponseEntity<Pagamento> buscar(@PathVariable Long id) {
        Pagamento pagamento = pagamentoService.buscarPorId(id)
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
        pagamento.add(linkTo(methodOn(PagamentoController.class).buscar(pagamento.getId()))
                .withSelfRel().withType("GET"));
        pagamento.add(linkTo(methodOn(PagamentoController.class).excluir(pagamento.getId()))
                .withRel("excluir").withType("DELETE"));
        pagamento.add(linkTo(methodOn(PagamentoController.class)
                .atualizar(new AtualizarPagamentoDTO(null, null)))
                .withRel("atualizar").withType("PUT"));
        pagamento.add(linkTo(methodOn(PagamentoController.class)
                .listar(new FiltroPagamentoDTO(null, null, null)))
                .withRel("listar").withType("GET"));
        return ResponseEntity.ok(pagamento);
    }
}
