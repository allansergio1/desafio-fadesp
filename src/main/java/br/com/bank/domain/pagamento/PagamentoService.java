package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.dto.AtualizarPagamentoDTO;
import br.com.bank.domain.pagamento.dto.PagamentoDTO;
import br.com.bank.domain.pagamento.enums.StatusPagamento;
import br.com.bank.domain.pagamento.exception.StatusPagamentoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Transactional
    public Pagamento receber(PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = new Pagamento();
        pagamento.fromDTO(pagamentoDTO);
        pagamento.setStatus(StatusPagamento.PENDENTE);
        return salvar(pagamento);
    }

    public Pagamento salvar(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    public void deletar(Long id) {
        pagamentoRepository.deleteById(id);
    }

    public Optional<Pagamento> buscarPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    public Iterable<Pagamento> buscarTodos() {
        return pagamentoRepository.findAll();
    }

    @Transactional
    public Pagamento atualizar(AtualizarPagamentoDTO atualizarDTO) {
        Pagamento pagamento = buscarPorId(atualizarDTO.id()).orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
        switch (pagamento.getStatus()) {
            case PENDENTE -> pagamento.setStatus(atualizarDTO.status());
            case SUCESSO -> throw new StatusPagamentoException("Pagamento já foi realizado com SUCESSO");
            case FALHA -> {
                if (!atualizarDTO.status().equals(StatusPagamento.PENDENTE)) {
                    throw new StatusPagamentoException("Pagamento com FALHA só pode ser alterado para o status PENDENTE");
                } else {
                    pagamento.setStatus(atualizarDTO.status());
                }
            }
        }
        return salvar(pagamento);
    }
}
