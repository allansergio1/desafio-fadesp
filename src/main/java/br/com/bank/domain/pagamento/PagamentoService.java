package br.com.bank.domain.pagamento;

import br.com.bank.domain.pagamento.dto.AtualizarPagamentoDTO;
import br.com.bank.domain.pagamento.dto.FiltroPagamentoDTO;
import br.com.bank.domain.pagamento.dto.PagamentoDTO;
import br.com.bank.domain.pagamento.enums.MetodoPagamento;
import br.com.bank.domain.pagamento.enums.StatusPagamento;
import br.com.bank.domain.pagamento.exception.MetodoPagamentoException;
import br.com.bank.domain.pagamento.exception.StatusPagamentoException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private static final Logger log = LoggerFactory.getLogger(PagamentoService.class);

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    @Transactional
    public Pagamento receber(PagamentoDTO pagamentoDTO) {
        log.info("Iniciando recebimento de pagamento...");
        validacaoPagamento(pagamentoDTO);
        Pagamento pagamento = convertToEntity(pagamentoDTO);
        pagamento.setStatus(StatusPagamento.PENDENTE);
        return salvar(pagamento);
    }

    public Pagamento salvar(Pagamento pagamento) {
        return pagamentoRepository.save(pagamento);
    }

    @Transactional
    public void excluir(Long id) {
        log.info("Iniciando exclusão de pagamento...");
        Pagamento pagamento = buscarPorId(id).orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
        if (pagamento.getStatus().equals(StatusPagamento.PENDENTE)) {
            pagamento.setAtivo(false);
            salvar(pagamento);
        } else {
            throw new StatusPagamentoException("Pagamento não pode ser excluído pois já foi processado");
        }
    }

    public Optional<Pagamento> buscarPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    public List<Pagamento> listar(FiltroPagamentoDTO filtro) {
        Specification<Pagamento> spec = Specification.where(PagamentoSpecification.comFiltro(filtro));
        return pagamentoRepository.findAll(spec);
    }

    @Transactional
    public Pagamento atualizar(AtualizarPagamentoDTO atualizarDTO) {
        log.info("Iniciando atualização de pagamento...");
        Pagamento pagamento = buscarPorId(atualizarDTO.id())
                .orElseThrow(() -> new EntityNotFoundException("Pagamento não encontrado"));
        switch (pagamento.getStatus()) {
            case PENDENTE -> pagamento.setStatus(atualizarDTO.status());
            case PROCESSADO -> throw new StatusPagamentoException("Pagamento já foi realizado com SUCESSO");
            case PROCESSADO_COM_FALHA -> {
                if (!atualizarDTO.status().equals(StatusPagamento.PENDENTE)) {
                    throw new StatusPagamentoException("Pagamento com FALHA só pode ser alterado para o status PENDENTE");
                } else {
                    pagamento.setStatus(atualizarDTO.status());
                }
            }
        }
        return salvar(pagamento);
    }

    public Pagamento convertToEntity(PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = new Pagamento();
        pagamento.setCodigoDebito(pagamentoDTO.codigoDebito());
        pagamento.setCpfCnpj(pagamentoDTO.cpfCnpj());
        pagamento.setMetodo(pagamentoDTO.metodo());
        pagamento.setValor(pagamentoDTO.valor());
        pagamento.setNumeroCartao(pagamentoDTO.numeroCartao());
        return pagamento;
    }

    public void validacaoPagamento(PagamentoDTO pagamentoDTO) {
        log.info("Validando pagamento...");
        boolean hasMetodoCartao = MetodoPagamento.getMetodosCartao().contains(pagamentoDTO.metodo());
        boolean hasNumeroCartao = StringUtils.hasText(pagamentoDTO.numeroCartao());
        if (hasMetodoCartao && !hasNumeroCartao) {
            throw new MetodoPagamentoException("Método de pagamento por cartão requer número do cartão");
        }
        if (!hasMetodoCartao && hasNumeroCartao) {
            throw new MetodoPagamentoException("Método de pagamento não requer número do cartão");
        }
    }
}
