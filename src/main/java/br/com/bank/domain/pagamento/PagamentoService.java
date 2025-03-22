package br.com.bank.domain.pagamento;

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
}
